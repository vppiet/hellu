package xyz.vppiet.hellu.services.weather.models;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import org.jetbrains.annotations.Nullable;
import xyz.vppiet.hellu.json.DataModel;

import java.util.List;
import java.util.Locale;

@GenerateTypeAdapter
@AutoValue
public abstract class CurrentConditionsSuccessModel implements DataModel {
	public static CurrentConditionsSuccessModel create(
			Coord coord,
			List<Weather> weather,
			String base,
			Main main,
			int visibility,
			Wind wind,
			Clouds clouds,
			@Nullable Rain rain,
			@Nullable Snow snow,
			int dt,
			Sys sys,
			int timezone,
			int id,
			String name,
			int cod) {
		return new AutoValue_CurrentConditionsSuccessModel(
				coord,
				weather,
				base,
				main,
				visibility,
				wind,
				clouds,
				rain,
				snow,
				dt,
				sys,
				timezone,
				id,
				name,
				cod);
	}

	public abstract Coord coord();
	public abstract List<Weather> weather();
	public abstract String base();
	public abstract Main main();
	public abstract int visibility();
	public abstract Wind wind();
	public abstract Clouds clouds();
	@Nullable public  abstract Rain rain();
	@Nullable public abstract Snow snow();
	public abstract int dt();
	public abstract Sys sys();
	public abstract int timezone();
	public abstract int id();
	public abstract String name();
	public abstract int cod();

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Coord {
		static Coord create(float lon, float lat) {
			return new AutoValue_CurrentConditionsSuccessModel_Coord(lon, lat);
		}

		public abstract float lon();
		public abstract float lat();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Weather {
		static Weather create(int id, String main, String description, String icon) {
			return new AutoValue_CurrentConditionsSuccessModel_Weather(id, main, description, icon);
		}

		public abstract int id();
		public abstract String main();
		public abstract String description();
		public abstract String icon();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Main {
		static Main create(float temp, float feels_like, int pressure, int humidity,
						   float temp_min, float temp_max, int sea_level, int grnd_level) {
			return new AutoValue_CurrentConditionsSuccessModel_Main(temp, feels_like, pressure,
					humidity, temp_min, temp_max, sea_level, grnd_level);
		}

		public abstract float temp();
		public abstract float feels_like();
		public abstract int pressure();
		public abstract int humidity();
		public abstract float temp_min();
		public abstract float temp_max();
		public abstract int sea_level();
		public abstract int grnd_level();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Wind {
		static Wind create(float speed, int deg, float gust) {
			return new AutoValue_CurrentConditionsSuccessModel_Wind(speed, deg, gust);
		}

		public abstract float speed();
		public abstract int deg();
		public abstract float gust();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Clouds {
		static Clouds create(int all) {
			return new AutoValue_CurrentConditionsSuccessModel_Clouds(all);
		}

		public abstract int all();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Rain {
		static Rain create(float oneHour, float threeHours) {
			return new AutoValue_CurrentConditionsSuccessModel_Rain(oneHour, threeHours);
		}

		@SerializedName("1h")
		public abstract float oneHour();

		@SerializedName("3h")
		public abstract float threeHours();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Snow {
		static Snow create(float oneHour, float threeHours) {
			return new AutoValue_CurrentConditionsSuccessModel_Snow(oneHour, threeHours);
		}

		@SerializedName("1h")
		public abstract float oneHour();

		@SerializedName("3h")
		public abstract float threeHours();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Sys {
		static Sys create(int type, int id, float message, String country, int sunrise, int sunset) {
			return new AutoValue_CurrentConditionsSuccessModel_Sys(type, id, message, country, sunrise, sunset);
		}

		public abstract int type();
		public abstract int id();
		public abstract float message();
		public abstract String country();
		public abstract int sunrise();
		public abstract int sunset();
	}

	@Override
	public final String formatted() {
		StringBuilder formatted = new StringBuilder();

		String name = this.name();
		formatted.append(name).append(", ");

		String country = this.sys().country();
		formatted.append(country).append(": ");

		float temp = this.main().temp();
		String formattedTemp = String.format(Locale.ENGLISH, "%.1f", temp);
		formatted.append(formattedTemp).append("°C ");

		float feelsLike = this.main().feels_like();
		String formattedFeelsLike = String.format(Locale.ENGLISH, "%.1f", feelsLike);
		formatted.append("(").append(formattedFeelsLike).append("°C) ");

		int pressure = this.main().pressure();
		formatted.append(pressure).append(" hPa ");

		int humidity = this.main().humidity();
		formatted.append(humidity).append("% ");

		float windSpeed = this.wind().speed();
		String formattedWindSpeed = String.format(Locale.ENGLISH, "%.1f", windSpeed);
		formatted.append(formattedWindSpeed).append(" m/s ");

		float windGust = this.wind().gust();
		String formattedWindGust = String.format(Locale.ENGLISH, "%.1f", windGust);
		formatted.append("(").append(formattedWindGust).append(" m/s) ");

		String description = this.weather().get(0).description();
		formatted.append(description);

		return formatted.toString();
	}
}
