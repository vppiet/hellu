package xyz.vppiet.hellu.services.football;

import lombok.AccessLevel;
import lombok.Getter;
import xyz.vppiet.hellu.services.ServiceBase;

@Getter(AccessLevel.PACKAGE)
public final class FootballService extends ServiceBase {

	public static final String API_KEY_PROPERTY = "service.football.apifootball.key";

	private static final String NAME = "football";
	private static final String DESCRIPTION = "Contains football related commands.";

	private final ApiFootball apiFootball;

	public FootballService(String apiKey) {
		super(NAME, DESCRIPTION);
		this.apiFootball = new ApiFootball(apiKey);
	}
}
