package xyz.vppiet.hellu.external.openweathermap.models;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@AutoValue
@GenerateTypeAdapter
public abstract class OneCall {

	abstract double lat();
	abstract double lon();
	abstract String timezone();
	abstract int timezone_offset();
	abstract Current current();

	@AutoValue.Builder
	abstract static class Builder {
		abstract Builder setLat(double lat);
		abstract Builder setLon(double lon);
		abstract Builder setTimezone(String timezone);
		abstract Builder setTimezone_offset(int timezone_offset);
		abstract Builder setCurrent(Current current);
		abstract OneCall build();
	}

	@AutoValue
	@GenerateTypeAdapter
	abstract static class Current {

		abstract int dt();
		abstract double temp();
		@SerializedName("feels_like") abstract double feelsLike();
		abstract int pressure();
		abstract int humidity();
		@SerializedName("wind_speed") abstract double windSpeed();
		@SerializedName("wind_gust") @Nullable abstract Double windGust();
		abstract List<Weather> weather();

		static Builder builder() {
			return new AutoValue_OneCall_Current.Builder();
		}

		@AutoValue.Builder
		abstract static class Builder {
			abstract Builder setDt(int dt);
			abstract Builder setTemp(double temp);
			abstract Builder setFeelsLike(double feelsLike);
			abstract Builder setPressure(int pressure);
			abstract Builder setHumidity(int humidity);
			abstract Builder setWindSpeed(double windSpeed);
			abstract Builder setWindGust(@Nullable Double windGust);
			abstract Builder setWeather(List<Weather> weather);
			abstract Current build();
		}

		@AutoValue
		@GenerateTypeAdapter
		abstract static class Weather {

			abstract String description();

			static Builder builder() {
				return new AutoValue_OneCall_Current_Weather.Builder();
			}

			@AutoValue.Builder
			abstract static class Builder {
				abstract Builder setDescription(String description);
				abstract Weather build();
			}
		}
	}

	public final String getCurrentWeatherFormatted() {
		StringBuilder formatted = new StringBuilder();

		double temp = this.current().temp();
		String formattedTemp = String.format(Locale.ENGLISH, "%.1f", temp);
		formatted.append(formattedTemp).append("°C ");

		double feelsLike = this.current().feelsLike();
		String formattedFeelsLike = String.format(Locale.ENGLISH, "%.1f", feelsLike);
		formatted.append("(").append(formattedFeelsLike).append("°C) ");

		int pressure = this.current().pressure();
		formatted.append(pressure).append(" hPa ");

		int humidity = this.current().humidity();
		formatted.append(humidity).append("% ");

		double windSpeed = this.current().windSpeed();
		String formattedWindSpeed = String.format(Locale.ENGLISH, "%.1f", windSpeed);
		formatted.append(formattedWindSpeed).append(" m/s");

		Double windGust = this.current().windGust();
		if (Objects.nonNull(windGust)) {
			String formattedWindGust = String.format(Locale.ENGLISH, "%.1f", windGust);
			formatted.append(" (").append(formattedWindGust).append(" m/s)");
		}

		if (!this.current().weather().isEmpty()) {
			String description = this.current().weather().get(0).description();
			formatted.append(" ").append(description);
		}

		return formatted.toString();
	}
}
