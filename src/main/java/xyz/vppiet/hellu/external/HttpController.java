package xyz.vppiet.hellu.external;

import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public final class HttpController {

	private static final HttpClient CLIENT = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(10))
			.build();

	private HttpController() {
	}

	public static HttpClient getClient() {
		return CLIENT;
	}

	public static HttpRequest createGetRequest(URI uri, Map<String, String> headers) {
		HttpRequest.Builder builder = HttpRequest.newBuilder()
				.GET()
				.uri(uri);

		for (Map.Entry<String, String> header : headers.entrySet()) {
			String key = header.getKey();
			String value = header.getValue();
			builder.header(key, value);
		}

		return builder.build();
	}

	public static HttpRequest createJsonGetRequest(URI uri) {
		return createJsonGetRequest(uri, new HashMap<>());
	}

	public static HttpRequest createJsonGetRequest(URI uri, Map<String, String> headers) {
		headers.put("Accepts", "application/json");

		return createGetRequest(uri, headers);
	}

	public static URI createURI(String scheme, String host, String path, String query) throws URISyntaxException {
		return new URI(scheme, null, host, -1, path, query, null);
	}
}
