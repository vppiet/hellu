package xyz.vppiet.hellu.services.football;

import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.json.ErrorModel;
import xyz.vppiet.hellu.json.DataModel;
import xyz.vppiet.hellu.json.JsonProcessor;
import xyz.vppiet.hellu.services.football.models.FixturesModel;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.charset.StandardCharsets;

@Log4j2
public class FixturesBodyHandler implements BodyHandler<DataModel> {

	@Override
	public BodySubscriber<DataModel> apply(ResponseInfo responseInfo) {
		int statusCode = responseInfo.statusCode();

		log.info("Status code: {}", statusCode);

		switch (statusCode) {
			case HttpURLConnection.HTTP_OK:
				return ofJson(FixturesModel.class);
			default:
				return ofJson(ErrorModel.class);
		}
	}

	public static BodySubscriber<DataModel> ofJson(Class<? extends DataModel> targetType) {
		BodySubscriber<String> upstream = BodySubscribers.ofString(StandardCharsets.UTF_8);

		return BodySubscribers.mapping(
				upstream,
				(String body) -> JsonProcessor.getGson().fromJson(body, targetType)
		);
	}
}
