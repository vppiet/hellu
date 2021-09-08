package xyz.vppiet.hellu;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Map;

public class HttpController {
	public static final HttpClient CLIENT = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(10))
			.build();

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
}
