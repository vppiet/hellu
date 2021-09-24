package xyz.vppiet.hellu.external.openweathermap.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import xyz.vppiet.hellu.external.JsonProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class GeoCodingTest {

	@Test
	void deserializingOk() {
		Type GeoCodingOkModelCollectionType = new TypeToken<Collection<GeoCoding>>() {
		}.getType();
		Gson gson = JsonProcessor.getGson();

		double expectedLat = 51.5085;

		try (BufferedReader json = new BufferedReader(new FileReader("src/test/resources/openweathermap/geo_200_london.json"))) {
			Collection<GeoCoding> deserialized = gson.fromJson(json, GeoCodingOkModelCollectionType);
			Optional<Double> optLat = deserialized.stream()
					.filter(geocoding -> geocoding.country().equals("GB"))
					.map(GeoCoding::lat)
					.findFirst();
			if (optLat.isEmpty()) {
				fail("Latitude of London (GB) not found.");
			}

			double actualLat = optLat.get();
			assertEquals(expectedLat, actualLat);
		} catch (IOException e) {
			fail(e);
		}

		String expectedCountry = "FI";

		try (BufferedReader json = new BufferedReader(new FileReader("src/test/resources/openweathermap/geo_200_olari_fi.json"))) {
			Collection<GeoCoding> deserialized = gson.fromJson(json, GeoCodingOkModelCollectionType);
			Optional<String> optCountry = deserialized.stream()
					.filter(geocoding -> geocoding.name().equals("Olari"))
					.map(GeoCoding::country)
					.findFirst();
			if (optCountry.isEmpty()) {
				fail("Country of city Olari not found.");
			}

			String actualCountry = optCountry.get();
			assertEquals(expectedCountry, actualCountry);
		} catch (IOException e) {
			fail(e);
		}
	}

	@Test
	void deserializingEmpty() {
		Type GeoCodingOkModelCollectionType = new TypeToken<Collection<GeoCoding>>() {
		}.getType();
		Gson gson = JsonProcessor.getGson();

		try (BufferedReader json = new BufferedReader(new FileReader("src/test/resources/openweathermap/geo_200_notfound.json"))) {
			Collection<GeoCoding> deserialized = gson.fromJson(json, GeoCodingOkModelCollectionType);
			assertTrue(deserialized.isEmpty());
		} catch (IOException e) {
			fail(e);
		}
	}
}
