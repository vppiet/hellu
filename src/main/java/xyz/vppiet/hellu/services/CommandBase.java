package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.CommandProperties;
import xyz.vppiet.hellu.Subject;
import xyz.vppiet.hellu.external.SqlController;

import java.time.Instant;

@Log4j2
@ToString(onlyExplicitlyIncluded = true)
public abstract class CommandBase implements Command {

	@ToString.Include
	@Getter(AccessLevel.PUBLIC)
	protected final String service;

	@ToString.Include
	@Getter(AccessLevel.PUBLIC)
	protected final String name;

	@Getter(AccessLevel.PUBLIC)
	protected final String description;

	@Getter(AccessLevel.PUBLIC)
	protected final ParameterManager parameterManager;

	@Getter(AccessLevel.PACKAGE)
	protected final CommandMatchObservationDao commandMatchObservationDao;

	public CommandBase(String service, String name, String description,
					   ParameterManager parameterManager) {
		this.service = service;
		this.name = name;
		this.description = description;
		this.parameterManager = parameterManager;
		this.commandMatchObservationDao = new CommandMatchObservationDao(SqlController.getDataSource());
	}

	@Override
	public String getHelp() {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.getDescription());
		sb.append(" ");
		sb.append("Usage: ");
		sb.append(this.getUsage());

		return sb.toString();
	}

	@Override
	public String getUsage() {
		final StringBuilder sb = new StringBuilder()
				.append(CommandProperties.SERVICE_PREFIX)
				.append(this.getService())
				.append(CommandProperties.COMMAND_SEPARATOR)
				.append(this.getName());

		final String paramUsage = this.getParameterManager().getUsage();

		if (paramUsage.isEmpty()) {
			return sb.toString();
		}

		sb.append(CommandProperties.COMMAND_SEPARATOR).append(paramUsage);

		return sb.toString();
	}

	@Override
	public boolean matches(CommandInvocation ci) {
		final String service = ci.getService();
		final String command = ci.getCommand();

		if (!(service.equals(this.service) && command.equals(this.name))) return false;

		CommandMatchObservation cmo = CommandMatchObservation.builder()
				.setService(this.getService())
				.setCommand(this.getName())
				.setTimestamp(Instant.now())
				.build();
		this.getCommandMatchObservationDao().add(cmo);

		log.info("New command invocation: {}", cmo);

		return true;
	}

	@Override
	public boolean requiredParameterSizeMatches(CommandInvocation ci) {
		final int givenParamSize = ci.getParams().size();
		final int requiredSize = this.getParameterManager().getRequired().size();

		return givenParamSize >= requiredSize;
	}

	@Override
	public void onNext(Subject subj, Object obj) {
		if (obj instanceof ServicedMessage) {
			ServicedMessage sm = (ServicedMessage) obj;
			CommandInvocation ci = sm.getCommandInvocation();

			if (!this.matches(ci)) return;

			if (!this.requiredParameterSizeMatches(ci)) {
				String usage = "Usage: " + this.getUsage();
				sm.getReplyableEvent().sendReply(usage);
				return;
			}

			this.handleServicedMessage(sm);
		}
	}
}
