package xyz.vppiet.hellu.services.football;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.external.SqlController;
import xyz.vppiet.hellu.services.ServiceBase;

@Getter(AccessLevel.PACKAGE)
@Log4j2
public final class FootballService extends ServiceBase {

	private static final String NAME = "football";
	private static final String DESCRIPTION = "Contains football related commands.";

	private final ApiFootball apiFootball;

	public FootballService() {
		super(NAME, DESCRIPTION);
		this.apiFootball = new ApiFootball();
		this.initializeSchema();
	}

	@Override
	protected void initializeSchema() {
		SqlController.executeRaw(FootballSchema.META_SCHEMA);
		SqlController.executeRaw(FootballSchema.COUNTRY_TABLE);
		SqlController.executeRaw(FootballSchema.SEASON_SCHEMA);
	}
}
