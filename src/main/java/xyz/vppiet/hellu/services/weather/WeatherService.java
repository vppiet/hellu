package xyz.vppiet.hellu.services.weather;

import lombok.AccessLevel;
import lombok.Getter;
import xyz.vppiet.hellu.services.ServiceBase;

@Getter(AccessLevel.PACKAGE)
public final class WeatherService extends ServiceBase {

	public static final String API_KEY_PROPERTY = "service.weather.openweathermap.key";

	private static final String NAME = "weather";
	private static final String DESCRIPTION = "Contains weather related commands.";

	private final OpenWeatherMap openWeatherMap;

	public WeatherService(String apiKey) {
		super(NAME, DESCRIPTION);
		this.openWeatherMap = new OpenWeatherMap(apiKey);
	}
}
