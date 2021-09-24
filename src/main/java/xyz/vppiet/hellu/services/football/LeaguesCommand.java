package xyz.vppiet.hellu.services.football;

import lombok.ToString;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.ParameterManager;
import xyz.vppiet.hellu.services.ServicedMessage;

@ToString(callSuper = true)
public class LeaguesCommand extends CommandBase {

	private static final String SERVICE = "football";
	private static final String NAME = "leagues";
	private static final String DESCRIPTION = "Displays leagues available for a given country code (ISO 3166-1 alpha-2).";
	private static final ParameterManager PARAMS = new ParameterManager().addRequired("country code");

	public LeaguesCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedMessage(ServicedMessage sm) {

	}
}
