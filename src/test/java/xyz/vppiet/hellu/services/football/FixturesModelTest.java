package xyz.vppiet.hellu.services.football;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import xyz.vppiet.hellu.json.JsonProcessor;
import xyz.vppiet.hellu.services.football.models.FixturesModel;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class FixturesModelTest {

	@Test
	void deserializing() throws FileNotFoundException {
		String path = "src/test/resources/apifootball/fixtures_live_championship.json";
		FileReader json = new FileReader(path);

		FixturesModel fixtures = JsonProcessor.getGson().fromJson(json, FixturesModel.class);

		String expected = "Birmingham";
		String actual = fixtures.response().get(0).teams().home().name();

		assertEquals(expected, actual);
	}
}
