package xyz.vppiet.hellu.external.openweathermap;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.external.HttpController;
import xyz.vppiet.hellu.external.JsonProcessor;
import xyz.vppiet.hellu.external.openweathermap.models.GeoCoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Log4j2
final class GeoCodingHttpController {

	private GeoCodingHttpController() {
	}

	static Optional<BufferedReader> getGeoCoding(String city, String country) {
		StringBuilder query = new StringBuilder()
				.append("q=")
				.append(city);

		if (Objects.nonNull(country) && !country.isBlank()) {
			query.append(", ").append(country);
		}

		query.append("&limit=5").append("&appid=");

		try {
			URI uri = HttpController.createURI(
					OpenWeatherMap.SCHEME,
					OpenWeatherMap.HOST,
					OpenWeatherMap.GEOCODING_PATH,
					query + System.getenv(OpenWeatherMap.ENV_VAR)
			);
			HttpRequest request = HttpController.createJsonGetRequest(uri);
			HttpResponse<InputStream> response = HttpController.getClient()
					.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
					.get(5, TimeUnit.SECONDS);

			if (response.statusCode() != HttpURLConnection.HTTP_OK) {
				String errorBody = new BufferedReader(new InputStreamReader(response.body()))
						.lines()
						.collect(Collectors.joining("\n"));

				log.error("OpenWeatherMap API responded with a status {} and a body: {}", response.statusCode(), errorBody);
				return Optional.empty();
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()));

			return Optional.of(reader);
		} catch (URISyntaxException e) {
			log.error("Exception thrown during URI forming.", e);
			return Optional.empty();
		} catch (ExecutionException | InterruptedException | TimeoutException e) {
			log.error("Exception thrown during HTTP connection.", e);
			return Optional.empty();
		}
	}

	static String getGeoCodingFormatted(String city, String country) {
		Optional<BufferedReader> optReader = getGeoCoding(city, country);

		if (optReader.isEmpty()) {
			return "Something went wrong while fetching location coordinates.";
		}

		BufferedReader reader = optReader.get();

		try {
			Type targetType = new TypeToken<Collection<GeoCoding>>(){}.getType();
			Collection<GeoCoding> geo = JsonProcessor.getGson().fromJson(reader, targetType);

			if (geo.isEmpty()) return "No locations found.";

			return geo.stream().map(GeoCoding::getFormatted).collect(Collectors.joining(", "));
		} catch (JsonIOException | JsonSyntaxException e) {
			log.error("Exception thrown while deserializing JSON from a reader.", e);

			return "Something went wrong while processing location coordinates.";
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				log.error("Exception thrown while closing a reader.", e);
			}
		}
	}

	static Optional<Collection<GeoCoding>> getGeoCodingParsed(String city, String country) {
		Optional<BufferedReader> optReader = getGeoCoding(city, country);

		if (optReader.isEmpty()) return Optional.empty();

		BufferedReader reader = optReader.get();
		Type targetType = new TypeToken<Collection<GeoCoding>>(){}.getType();
		try {
			Collection<GeoCoding> geoCodings = JsonProcessor.getGson().fromJson(reader, targetType);
			return Optional.ofNullable(geoCodings);
 		} catch (JsonIOException | JsonSyntaxException e) {
			log.error("Exception thrown while deserializing JSON from a reader.", e);

			return Optional.empty();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				log.error("Exception thrown while closing a reader.", e);
			}
		}
	}
}
