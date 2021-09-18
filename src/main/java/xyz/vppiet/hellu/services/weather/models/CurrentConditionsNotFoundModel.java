package xyz.vppiet.hellu.services.weather.models;

import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import xyz.vppiet.hellu.json.DataModel;

@GenerateTypeAdapter
@AutoValue
public abstract class CurrentConditionsNotFoundModel implements DataModel {
	static CurrentConditionsNotFoundModel create(String cod, String message) {
		return new AutoValue_CurrentConditionsNotFoundModel(cod, message);
	}

	public abstract String cod();
	public abstract String message();

	@Override
	public final String formatted() {
		return "City not found.";
	}
}
