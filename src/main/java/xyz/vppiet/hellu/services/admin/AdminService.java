package xyz.vppiet.hellu.services.admin;

import xyz.vppiet.hellu.services.ServiceBase;

public class AdminService extends ServiceBase {

	public static final String ENV_VAR = "HELLU_PRIVILEGED_HOST";

	private static final String NAME = "admin";
	private static final String DESCRIPTION = "Admin service contains a set of administrative commands.";

	public AdminService() {
		super(NAME, DESCRIPTION);
	}
}
