package xyz.vppiet.hellu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;

public class JsonProcessor {

	public static final Gson GSON = new GsonBuilder()
			.registerTypeAdapterFactory(GenerateTypeAdapter.FACTORY)
			.create();
}
