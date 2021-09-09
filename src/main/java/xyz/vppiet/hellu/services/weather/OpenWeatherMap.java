package xyz.vppiet.hellu.services.weather;

import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.HttpController;
import xyz.vppiet.hellu.JsonModel;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Log4j2
class OpenWeatherMap {

	static final String SCHEME = "https";
	static final String HOST = "api.openweathermap.org";
	static final String CURRENT_CONDITIONS_PATH = "/data/2.5/weather";

	private final String apiKey;

	OpenWeatherMap(String apiKey) {
		this.apiKey = apiKey;
	}

	String getCurrentConditionsByCity(String city) {
		String query = "q=" + city + "&units=metric&appid=" + this.apiKey;        // TODO: escaping

		try {
			URI uri = new URI(SCHEME, null, HOST, -1, CURRENT_CONDITIONS_PATH, query, null);

			Map<String, String> headers = new HashMap<>();
			headers.put("Accepts", "application/json");

			HttpRequest request = HttpController.createGetRequest(uri, headers);

			JsonModel body = HttpController.CLIENT.sendAsync(request, new CurrentConditionsBodyHandler())
					.thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);

			if (body instanceof CurrentConditionsNotFoundModel) {
				CurrentConditionsNotFoundModel notFoundBody = (CurrentConditionsNotFoundModel) body;
				return "City not found.";
			}

			if (body instanceof CurrentConditionsSuccessModel) {
				CurrentConditionsSuccessModel successBody = (CurrentConditionsSuccessModel) body;

				StringBuilder reply = new StringBuilder();

				String name = successBody.name();
				reply.append(name).append(", ");

				String country = successBody.sys().country();
				reply.append(country).append(": ");

				float temp = successBody.main().temp();
				String formattedTemp = String.format(Locale.ENGLISH, "%.1f", temp);
				reply.append(formattedTemp).append("°C ");

				float feelsLike = successBody.main().feels_like();
				String formattedFeelsLike = String.format(Locale.ENGLISH, "%.1f", feelsLike);
				reply.append("(").append(formattedFeelsLike).append("°C) ");

				int pressure = successBody.main().pressure();
				reply.append(pressure).append(" hPa ");

				int humidity = successBody.main().humidity();
				reply.append(humidity).append("% ");

				float windSpeed = successBody.wind().speed();
				String formattedWindSpeed = String.format(Locale.ENGLISH, "%.1f", windSpeed);
				reply.append(formattedWindSpeed).append(" m/s ");

				float windGust = successBody.wind().gust();
				String formattedWindGust = String.format(Locale.ENGLISH, "%.1f", windGust);
				reply.append("(").append(formattedWindGust).append(" m/s) ");

				String description = successBody.weather().get(0).description();
				reply.append(description);

				return reply.toString();
			}

			return "Error: Something went wrong while retrieving weather data.";

		} catch (URISyntaxException ex) {
			log.error("Exception thrown during URI forming", ex);

			return "Error: Invalid city name.";
		} catch (TimeoutException ex) {
			log.error("HTTP request timed out", ex);

			return "Error: HTTP request timed out.";
		} catch (ExecutionException | InterruptedException ex) {
			log.error("Execution of the HTTP request was interrupted", ex);

			return "Error: Something went wrong while retrieving weather data.";
		}
	}
}
