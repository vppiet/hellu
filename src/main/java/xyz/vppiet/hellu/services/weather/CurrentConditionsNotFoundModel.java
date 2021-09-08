package xyz.vppiet.hellu.services.weather;

import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import xyz.vppiet.hellu.JsonModel;

@GenerateTypeAdapter
@AutoValue
abstract class CurrentConditionsNotFoundModel implements JsonModel {
	static CurrentConditionsNotFoundModel create(String cod, String message) {
		return new AutoValue_CurrentConditionsNotFoundModel(cod, message);
	}

	abstract String cod();
	abstract String message();
}
