package xyz.vppiet.hellu.external.openweathermap.models;

import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@AutoValue
@GenerateTypeAdapter
public abstract class GeoCoding {

	public abstract String name();
	public abstract double lat();
	public abstract double lon();
	public abstract String country();
	@Nullable abstract String state();

	static Builder builder() {
		return new AutoValue_GeoCoding.Builder();
	}

	@AutoValue.Builder
	abstract static class Builder {
		public abstract Builder setName(String name);
		public abstract Builder setLat(double lat);
		public abstract Builder setLon(double lon);
		public abstract Builder setCountry(String country);
		public abstract Builder setState(@Nullable String state);

		abstract GeoCoding build();
	}

	public final String getFormatted() {
		StringBuilder sb = new StringBuilder(this.name()).append(", ");

		if (Objects.nonNull(this.state())) {
			sb.append(this.state()).append(", ");
		}

		sb.append(this.country()).append(": ")
				.append(this.lat()).append("°, ")
				.append(this.lon()).append("°");

		return sb.toString();
	}
}
