package xyz.vppiet.hellu.services.weather;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class OpenWeatherMapTest {

	@Test
	void getCurrentConditionsByCity() {
		OpenWeatherMap openWeatherMap = new OpenWeatherMap("apiKey");
		String reply = openWeatherMap.getCurrentConditionsByCity("Kuusamo");
		log.info(reply);
	}
}
