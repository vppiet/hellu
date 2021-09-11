package xyz.vppiet.hellu.services.football;

import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.HttpController;
import xyz.vppiet.hellu.JsonModel;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Log4j2
final class ApiFootball {

	static final String SCHEME = "https";
	static final String HOST = "v3.football.api-sports.io";
	static final String LEAGUES_PATH = "/leagues";
	static final String FIXTURES_PATH = "/fixtures";

	private final String apiKey;

	ApiFootball(String apiKey) {
		this.apiKey = apiKey;
	}

	Optional<LeaguesModel> getCurrentLeaguesByCountryCode(String countryCode) {
		String query = "current=true&code=" + countryCode;

		try {
			URI uri = new URI(SCHEME, null, HOST, -1, LEAGUES_PATH, query, null);

			Map<String, String> headers = new HashMap<>();
			headers.put("Accepts", "application/json");
			headers.put("x-apisports-key", this.apiKey);

			HttpRequest request = HttpController.createGetRequest(uri, headers);

			JsonModel body = HttpController.CLIENT.sendAsync(request, new LeaguesBodyHandler())
					.thenApply(HttpResponse::body).get(30, TimeUnit.SECONDS);

			if (body instanceof LeaguesModel) {
				LeaguesModel successBody = (LeaguesModel) body;
				return Optional.of(successBody);
			}

			return Optional.empty();
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

	Optional<FixturesModel> getLiveFixturesByLeague(int league) {
		String query = "live=all&league=" + league;

		try {
			URI uri = new URI(SCHEME, null, HOST, -1, FIXTURES_PATH, query, null);

			Map<String, String> headers = new HashMap<>();
			headers.put("Accepts", "application/json");
			headers.put("x-apisports-key", this.apiKey);

			HttpRequest request = HttpController.createGetRequest(uri, headers);

			JsonModel body = HttpController.CLIENT.sendAsync(request, new FixturesBodyHandler())
					.thenApply(HttpResponse::body).get(30, TimeUnit.SECONDS);

			if (body instanceof FixturesModel) {
				FixturesModel successBody = (FixturesModel) body;

				return Optional.of(successBody);
			}

			return Optional.empty();
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
