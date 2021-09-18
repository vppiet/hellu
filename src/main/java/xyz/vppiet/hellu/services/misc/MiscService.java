package xyz.vppiet.hellu.services.misc;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.services.ServiceBase;

@Log4j2
@ToString(callSuper = true)
public class MiscService extends ServiceBase {

	private static final String NAME = "misc";
	private static final String DESCRIPTION = "Misc service contains an assortment of unrelated commands.";

	public MiscService() {
		super(NAME, DESCRIPTION);
	}

	@Override
	protected void initializeSchema() {

	}
}
