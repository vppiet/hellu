package xyz.vppiet.hellu.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;

public final class JsonProcessor {

	private static final Gson GSON = new GsonBuilder()
			.registerTypeAdapterFactory(GenerateTypeAdapter.FACTORY)
			.create();

	private JsonProcessor() {}

	public static Gson getGson() {
		return GSON;
	}
}
