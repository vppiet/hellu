package xyz.vppiet.hellu.external.openweathermap;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.external.HttpController;
import xyz.vppiet.hellu.external.JsonProcessor;
import xyz.vppiet.hellu.external.openweathermap.models.OneCall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Log4j2
final class OneCallHttpController {

	private OneCallHttpController() {
	}

	static Optional<BufferedReader> getCurrentWeather(double lat, double lon) {
		String query = new StringBuilder()
				.append("lat=").append(lat)
				.append("&lon=").append(lon)
				.append("&exclude=minutely,hourly,daily,alerts")
				.append("&units=metric")
				.append("&appid=")
				.toString();

		try {
			URI uri = HttpController.createURI(
					OpenWeatherMap.SCHEME,
					OpenWeatherMap.HOST,
					OpenWeatherMap.ONE_CALL_PATH,
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

	static String getCurrentWeatherFormatted(double lat, double lon) {
		Optional<BufferedReader> optReader = getCurrentWeather(lat, lon);

		if (optReader.isEmpty()) {
			return "Something went wrong while fetching current weather data.";
		}

		BufferedReader reader = optReader.get();

		try {
			OneCall weather = JsonProcessor.getGson().fromJson(reader, OneCall.class);

			return weather.getCurrentWeatherFormatted();
		} catch (JsonIOException | JsonSyntaxException e) {
			log.error("Exception thrown while deserializing JSON from a reader.", e);

			return "Something went wrong while processing current weather data.";
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				log.error("Exception thrown while closing a reader.", e);
			}
		}
	}
}
