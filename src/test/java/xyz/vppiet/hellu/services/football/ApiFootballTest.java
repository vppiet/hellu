package xyz.vppiet.hellu.services.football;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import xyz.vppiet.hellu.services.football.models.FixturesModel;
import xyz.vppiet.hellu.services.football.models.LeaguesModel;

import java.util.Optional;

@Log4j2
class ApiFootballTest {

	@Test
	void getCurrentLeaguesByCountryCode() {
		ApiFootball apiFootball = new ApiFootball();
		Optional<LeaguesModel> optLeagues = apiFootball.getCurrentLeaguesByCountryCode("GB");

		if (optLeagues.isPresent()) {
			LeaguesModel leagues = optLeagues.get();

			for (LeaguesModel.Response response : leagues.response()) {
				LeaguesModel.Response.League league = response.league();

				int id = league.id();
				String name = league.name();
				String type = league.type();

				log.info("{}\t{}\t{}", id, name, type);
			}
		}
	}

	@Test
	void getLiveFixturesByLeague() {
		ApiFootball apiFootball = new ApiFootball();
		Optional<FixturesModel> optFixtures = apiFootball.getLiveFixturesByLeague(40);

		if (optFixtures.isPresent()) {
			FixturesModel fixtures = optFixtures.get();

			log.info(fixtures);
		}
	}
}
