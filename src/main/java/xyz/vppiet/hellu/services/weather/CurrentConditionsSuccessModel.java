package xyz.vppiet.hellu.services.weather;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import org.jetbrains.annotations.Nullable;
import xyz.vppiet.hellu.JsonModel;

import java.util.List;

@GenerateTypeAdapter
@AutoValue
public abstract class CurrentConditionsSuccessModel implements JsonModel {
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

	abstract Coord coord();
	abstract List<Weather> weather();
	abstract String base();
	abstract Main main();
	abstract int visibility();
	abstract Wind wind();
	abstract Clouds clouds();
	@Nullable abstract Rain rain();
	@Nullable abstract Snow snow();
	abstract int dt();
	abstract Sys sys();
	abstract int timezone();
	abstract int id();
	abstract String name();
	abstract int cod();

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Coord {
		static Coord create(float lon, float lat) {
			return new AutoValue_CurrentConditionsSuccessModel_Coord(lon, lat);
		}

		abstract float lon();
		abstract float lat();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Weather {
		static Weather create(int id, String main, String description, String icon) {
			return new AutoValue_CurrentConditionsSuccessModel_Weather(id, main, description, icon);
		}

		abstract int id();
		abstract String main();
		abstract String description();
		abstract String icon();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Main {
		static Main create(float temp, float feels_like, int pressure, int humidity,
						   float temp_min, float temp_max, int sea_level, int grnd_level) {
			return new AutoValue_CurrentConditionsSuccessModel_Main(temp, feels_like, pressure,
					humidity, temp_min, temp_max, sea_level, grnd_level);
		}

		abstract float temp();
		abstract float feels_like();
		abstract int pressure();
		abstract int humidity();
		abstract float temp_min();
		abstract float temp_max();
		abstract int sea_level();
		abstract int grnd_level();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Wind {
		static Wind create(float speed, int deg, float gust) {
			return new AutoValue_CurrentConditionsSuccessModel_Wind(speed, deg, gust);
		}

		abstract float speed();
		abstract int deg();
		abstract float gust();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Clouds {
		static Clouds create(int all) {
			return new AutoValue_CurrentConditionsSuccessModel_Clouds(all);
		}

		abstract int all();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Rain {
		static Rain create(float oneHour, float threeHours) {
			return new AutoValue_CurrentConditionsSuccessModel_Rain(oneHour, threeHours);
		}

		@SerializedName("1h")
		abstract float oneHour();

		@SerializedName("3h")
		abstract float threeHours();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Snow {
		static Snow create(float oneHour, float threeHours) {
			return new AutoValue_CurrentConditionsSuccessModel_Snow(oneHour, threeHours);
		}

		@SerializedName("1h")
		abstract float oneHour();

		@SerializedName("3h")
		abstract float threeHours();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Sys {
		static Sys create(int type, int id, float message, String country, int sunrise, int sunset) {
			return new AutoValue_CurrentConditionsSuccessModel_Sys(type, id, message, country, sunrise, sunset);
		}

		abstract int type();
		abstract int id();
		abstract float message();
		abstract String country();
		abstract int sunrise();
		abstract int sunset();
	}
}
