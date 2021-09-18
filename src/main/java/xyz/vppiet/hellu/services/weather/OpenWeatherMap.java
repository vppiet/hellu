package xyz.vppiet.hellu.services.weather;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.external.HttpController;
import xyz.vppiet.hellu.json.DataModel;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Getter(AccessLevel.PACKAGE)
@Log4j2
final class OpenWeatherMap {

	private static final String ENV_VAR = "HELLU_OPENWEATHERMAP";
	private static final String SCHEME = "https";
	private static final String HOST = "api.openweathermap.org";
	private static final String CURRENT_CONDITIONS_PATH = "/data/2.5/weather";

	OpenWeatherMap() {
	}

	String getCurrentConditionsByCityFormatted(String city) {
		Optional<DataModel> body = this.getCurrentConditionsByCity(city);

		if (body.isEmpty()) {
			return "Something went wrong while retrieving current weather conditions.";
		}

		return body.get().formatted();
	}

	Optional<DataModel> getCurrentConditionsByCity(String city) {
		String query = "q=" + city + "&units=metric&appid=";        // TODO: escaping

		try {
			URI uri = HttpController.createURI(SCHEME, HOST, CURRENT_CONDITIONS_PATH, query + System.getenv(ENV_VAR));
			HttpRequest request = HttpController.createJsonGetRequest(uri);
			HttpResponse.BodyHandler<DataModel> bodyHandler = CurrentConditionsModelHandler.getBodyHandler();
			DataModel body = HttpController.getClient().sendAsync(request, bodyHandler)
					.thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);

			return Optional.ofNullable(body);
		} catch (URISyntaxException ex) {
			log.error("Exception thrown during URI forming", ex);
			return Optional.empty();
		} catch (TimeoutException ex) {
			log.error("HTTP request timed out", ex);
			return Optional.empty();
		} catch (ExecutionException | InterruptedException ex) {
			log.error("Execution of the HTTP request was interrupted", ex);
			return Optional.empty();
		}
	}
}
