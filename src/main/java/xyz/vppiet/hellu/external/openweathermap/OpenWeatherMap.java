package xyz.vppiet.hellu.external.openweathermap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.external.openweathermap.models.GeoCoding;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter(AccessLevel.PACKAGE)
@Log4j2
public final class OpenWeatherMap {

	static final String ENV_VAR = "HELLU_OPENWEATHERMAP";
	static final String SCHEME = "https";
	static final String HOST = "api.openweathermap.org";
	static final String CURRENT_CONDITIONS_PATH = "/data/2.5/weather";
	static final String GEOCODING_PATH = "/geo/1.0/direct";
	static final String ONE_CALL_PATH = "/data/2.5/onecall";

	private OpenWeatherMap() {
	}

	public static String getGeoCodingFormatted(String city, String country) {
		return GeoCodingHttpController.getGeoCodingFormatted(city, country);
	}

	public static String getCurrentWeatherFormatted(String city, String country) {
		Optional<Collection<GeoCoding>> optGeoCodings = GeoCodingHttpController.getGeoCodingParsed(city, country);
		if (optGeoCodings.isEmpty()) return "No locations found.";

		Collection<GeoCoding> geoCodings = optGeoCodings.get();
		if (geoCodings.isEmpty()) return "No locations found.";

		Set<String> countries = new HashSet<>();
		geoCodings.forEach(gc -> {
			countries.add(gc.country());
		});

		if (countries.size() > 1) {
			StringBuilder reply = new StringBuilder("Multiple locations found. Specify with a country code: ");
			String locations = geoCodings.stream()
					.filter(gc -> {
						if (countries.contains(gc.country())) {
							countries.remove(gc.country());
							return true;
						}
						return false;
					})
					.map(gc -> new StringBuilder(gc.name()).append(", ").append(gc.country()))
					.collect(Collectors.joining("; "));
			reply.append(locations);
			return reply.toString();
		}

		GeoCoding location = geoCodings.stream().findFirst().get();

		return new StringBuilder()
				.append(location.name())
				.append(", ")
				.append(location.country())
				.append(": ")
				.append(OneCallHttpController.getCurrentWeatherFormatted(location.lat(), location.lon()))
				.toString();
	}
}
