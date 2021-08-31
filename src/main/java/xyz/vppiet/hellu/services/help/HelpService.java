package xyz.vppiet.hellu.services.help;

import xyz.vppiet.hellu.services.ServiceBase;

public class HelpService extends ServiceBase {
	private static final String NAME = "help";
	private static final String DESCRIPTION =
			"All commands belong to a service. Show all services by issuing '.help services'.";

	public HelpService() {
		super(NAME, DESCRIPTION);
	}
}
