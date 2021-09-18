package xyz.vppiet.hellu.services.weather;

import xyz.vppiet.hellu.external.HttpController;
import xyz.vppiet.hellu.json.DataModel;
import xyz.vppiet.hellu.services.weather.models.CurrentConditionsNotFoundModel;
import xyz.vppiet.hellu.services.weather.models.CurrentConditionsSuccessModel;

import java.net.http.HttpResponse.BodyHandler;
import java.util.HashMap;
import java.util.Map;

final class CurrentConditionsModelHandler {

	private static final Map<Integer, Class<? extends DataModel>> STATUS_MODEL_MAPPING = new HashMap<>();

	static {
		STATUS_MODEL_MAPPING.put(200, CurrentConditionsSuccessModel.class);
		STATUS_MODEL_MAPPING.put(404, CurrentConditionsNotFoundModel.class);
	}

	private static final BodyHandler<DataModel> BODY_HANDLER = HttpController.createJsonBodyHandler(STATUS_MODEL_MAPPING);

	private CurrentConditionsModelHandler() {}

	static BodyHandler<DataModel> getBodyHandler() {
		return BODY_HANDLER;
	}
}
