package xyz.vppiet.hellu.services.football;

import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.EmptyJson;
import xyz.vppiet.hellu.JsonModel;
import xyz.vppiet.hellu.JsonProcessor;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.charset.StandardCharsets;

@Log4j2
final class LeaguesBodyHandler implements BodyHandler<JsonModel> {

	@Override
	public BodySubscriber<JsonModel> apply(ResponseInfo responseInfo) {
		int statusCode = responseInfo.statusCode();

		log.info("Status code: {}", statusCode);

		switch (statusCode) {
			case HttpURLConnection.HTTP_OK:
				return ofJson(AutoValue_LeaguesModel.class);
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
