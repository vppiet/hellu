package xyz.vppiet.hellu.services.football;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.CommandParameterManager;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;
import xyz.vppiet.hellu.services.StringCommandParameter;

import java.util.List;
import java.util.stream.Collectors;

public class LeaguesCommand extends CommandBase {

	private static final String SERVICE = "football";
	private static final String NAME = "leagues";
	private static final String DESCRIPTION = "Displays leagues available for a given country code (ISO 3166-1 alpha-2)";
	private static final CommandParameterManager PARAMS = new CommandParameterManager(
			new StringCommandParameter("country code", "")
	);

	public LeaguesCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {
		Service sourceService = scm.getSourceService();

		if (!(sourceService instanceof FootballService)) return;

		CommandInvocation ci = scm.getCommandInvocation();
		String country = ci.getParams().get(0).toUpperCase();

		ReplyableEvent event = scm.getEvent();

		if (country.length() != 2) {
			event.sendReply("Country code should be the length of two according to ISO 3166-1 alpha-2 standard. " +
					"(e.g. \"FI\")");
		}

		FootballService footballService = (FootballService) sourceService;
		List<LeaguesModel.Response.League> leagues = footballService.getLeaguesByCountry(country);

		if (leagues.isEmpty()) {
			event.sendReply("No leagues loaded for country code \"" + country + "\".");

			return;
		}

		String formattedLeagues = leagues.stream()
				.map(LeaguesModel.Response.League::name)
				.collect(Collectors.joining(", "));

		event.sendReply(country + ": " + formattedLeagues);
	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {

	}
}
