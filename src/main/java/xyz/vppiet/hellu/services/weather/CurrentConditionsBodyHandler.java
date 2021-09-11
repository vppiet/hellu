package xyz.vppiet.hellu.services.weather;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.EmptyJson;
import xyz.vppiet.hellu.JsonModel;
import xyz.vppiet.hellu.JsonProcessor;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;

@Log4j2
public class CurrentConditionsBodyHandler implements BodyHandler<JsonModel> {

	@Override
	public BodySubscriber<JsonModel> apply(HttpResponse.ResponseInfo responseInfo) {
		int statusCode = responseInfo.statusCode();

		log.info("Status code: {}", statusCode);

		switch (statusCode) {
			case HttpURLConnection.HTTP_NOT_FOUND:
				return ofJson(CurrentConditionsNotFoundModel.class);
			case HttpURLConnection.HTTP_OK:
				return ofJson(CurrentConditionsSuccessModel.class);
			default:
				return ofJson(EmptyJson.class);
		}
	}

	public static BodySubscriber<JsonModel> ofJson(Class<? extends JsonModel> targetType) {
		BodySubscriber<String> upstream = BodySubscribers.ofString(StandardCharsets.UTF_8);

		return BodySubscribers.mapping(
				upstream,
				(String body) -> JsonProcessor.GSON.fromJson(body, targetType)
		);
	}
}
