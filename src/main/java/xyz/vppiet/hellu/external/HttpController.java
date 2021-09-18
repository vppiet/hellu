package xyz.vppiet.hellu.external;

import xyz.vppiet.hellu.json.ErrorModel;
import xyz.vppiet.hellu.json.DataModel;
import xyz.vppiet.hellu.json.JsonProcessor;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.ResponseInfo;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class HttpController {

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

	public static BodyHandler<DataModel> createJsonBodyHandler(Map<Integer, Class<? extends DataModel>> statusModelMapping) {
		return new BodyHandler<>() {

			@Override
			public BodySubscriber<DataModel> apply(ResponseInfo responseInfo) {
				int statusCode = responseInfo.statusCode();

				Class<? extends DataModel> model = statusModelMapping.getOrDefault(statusCode, ErrorModel.class);

				return ofJson(model);
			}

			private BodySubscriber<DataModel> ofJson(Class<? extends DataModel> targetType) {
				BodySubscriber<String> upstream = BodySubscribers.ofString(StandardCharsets.UTF_8);

				return BodySubscribers.mapping(
						upstream,
						(String body) -> JsonProcessor.getGson().fromJson(body, targetType)
				);
			}
		};
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
