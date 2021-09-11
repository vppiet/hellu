package xyz.vppiet.hellu.services.football;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.services.ServiceBase;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter(AccessLevel.PACKAGE)
@Log4j2
public final class FootballService extends ServiceBase {

	public static final String API_KEY_PROPERTY = "service.football.apifootball.key";

	private static final String NAME = "football";
	private static final String DESCRIPTION = "Contains football related commands.";

	private final ApiFootball apiFootball;
	private final Set<LeaguesModel.Response.League> leagues;

	public FootballService(String apiKey) {
		super(NAME, DESCRIPTION);
		this.apiFootball = new ApiFootball(apiKey);
		this.leagues = Collections.synchronizedSet(new HashSet<>());
	}

	public Optional<Integer> getLeagueId(String leagueName) {
		Optional<LeaguesModel.Response.League> optLeague = this.getLeagues().stream()
				.filter(l -> l.name().equals(leagueName))
				.findFirst();

		if (optLeague.isEmpty()) {
			return Optional.empty();
		}

		LeaguesModel.Response.League league = optLeague.get();
		int id = league.id();

		return Optional.of(id);
	}

	public boolean hasLeague(String league) {
		return this.getLeagues().stream()
				.map(LeaguesModel.Response.League::name)
				.anyMatch(name -> name.equals(league));
	}

	public void loadLeaguesByCountry(String countryCode) {
		Optional<LeaguesModel> optLeagues = this.getApiFootball().getCurrentLeaguesByCountryCode(countryCode);

		if (optLeagues.isEmpty()) {
			log.error("Couldn't load leagues");

			return;
		}

		LeaguesModel leaguesModel = optLeagues.get();
		Set<LeaguesModel.Response.League> leagues = leaguesModel.response().stream()
				.map(LeaguesModel.Response::league).collect(Collectors.toUnmodifiableSet());
		this.getLeagues().addAll(leagues);
		log.info("Leagues loaded for country code {}", countryCode);
	}

	public List<LeaguesModel.Response.League> getLeaguesByCountry(String country) {
		// FIXME: 11.9.2021 
		// return this.getLeagues().stream().filter(l -> l.).collect(Collectors.toUnmodifiableList());
		return null;
	}
}
