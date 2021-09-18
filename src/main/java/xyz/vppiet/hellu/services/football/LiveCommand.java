package xyz.vppiet.hellu.services.football;

import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;

import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.CommandParameterManager;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;
import xyz.vppiet.hellu.services.StringCommandParameter;
import xyz.vppiet.hellu.services.football.models.FixturesModel;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public final class LiveCommand extends CommandBase {

	private static final String SERVICE = "football";
	private static final String NAME = "live";
	private static final String DESCRIPTION = "Displays live fixtures for a given league.";
	private static final CommandParameterManager PARAMS = new CommandParameterManager(
			new StringCommandParameter("league", "")
	);

	public LiveCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	public void handleServicedChannelMessageAlt(ServicedChannelMessage scm) {
		ReplyableEvent event = scm.getEvent();
		CommandInvocation ci = scm.getCommandInvocation();

		this.handleEvent(event, ci);
	}

	public void handleServicedPrivateMessageAlt(ServicedPrivateMessage spm) {
		ReplyableEvent event = spm.getEvent();
		CommandInvocation ci = spm.getCommandInvocation();

		this.handleEvent(event, ci);
	}

	private void handleEvent(ReplyableEvent event, CommandInvocation ci) {
		Optional<String> optReply = this.getReply(ci);
		if (optReply.isEmpty()) return;

		String reply = optReply.get();
		event.sendReply(reply);
	}

	private Optional<String> getReply(CommandInvocation ci) {
		String league = ci.getParams().get(0);

		return Optional.empty();        // FIXME: 16.9.2021
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {
/*		Service sourceService = scm.getSourceService();

		if (!(sourceService instanceof FootballService)) return;

		FootballService footballService = (FootballService) sourceService;

		CommandInvocation ci = scm.getCommandInvocation();
		String leagueParam = ci.getParams().get(0);

		ReplyableEvent event = scm.getEvent();

		ApiFootball apiFootball = footballService.getApiFootball();
		Optional<Integer> optLeagueId = footballService.getLeagueId(leagueParam);

		if (optLeagueId.isEmpty()) {
			event.sendReply("League ID not found.");

			return;
		}

		int leagueId = optLeagueId.get();
		Optional<FixturesModel> optFixtures = apiFootball.getLiveFixturesByLeague(leagueId);

		if (optFixtures.isEmpty()) {
			event.sendReply("Something went wrong while retrieving live matches.");

			return;
		}

		// Veikkausliiga: KuPS - SJK 15' 2-1, HJK - HIFK 30' 3-1
		FixturesModel fixtures = optFixtures.get();

		if (fixtures.results() == 0) {
			event.sendReply("No live matches currently ongoing.");

			return;
		}

		String leagueName = fixtures.response().get(0).league().name();

		StringBuilder reply = new StringBuilder(leagueName).append(": ");

		String formattedFixtures = fixtures.response().stream()
				.map(fixture -> {
					String homeTeam = fixture.teams().home().name();
					String awayTeam = fixture.teams().away().name();
					int elapsed = fixture.fixture().status().elapsed();
					int homeTeamScore = fixture.goals().home();
					int awayTeamScore = fixture.goals().away();

					StringBuilder str = new StringBuilder()
							.append(homeTeam)
							.append(" - ")
							.append(awayTeam)
							.append(" ")
							.append(elapsed)
							.append("' ")
							.append(homeTeamScore)
							.append("-")
							.append(awayTeamScore);

					return str.toString();
				})
				.collect(Collectors.joining(", "));

		reply.append(formattedFixtures);

		event.sendReply(reply.toString());*/
	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {

	}
}
