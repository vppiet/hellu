package xyz.vppiet.hellu.services.football;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.external.SqlController;
import xyz.vppiet.hellu.services.ServiceBase;

@Getter(AccessLevel.PACKAGE)
@Log4j2
@ToString(callSuper = true)
public final class FootballService extends ServiceBase {

	private static final String NAME = "football";
	private static final String DESCRIPTION = "Contains football related commands.";

	public FootballService() {
		super(NAME, DESCRIPTION);
		this.initializeSchema();
	}

	private void initializeSchema() {
		SqlController.executeRaw(FootballSchema.UPDATED_SCHEMA);
		SqlController.executeRaw(FootballSchema.COUNTRY_SCHEMA);
		SqlController.executeRaw(FootballSchema.SEASON_SCHEMA);
	}
}
