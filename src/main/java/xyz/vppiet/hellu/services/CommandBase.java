package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.CommandProperties;
import xyz.vppiet.hellu.Subject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@Log4j2
@ToString
public abstract class CommandBase implements Command {

	protected final String service;
	protected final String name;
	protected final String description;
	protected final CommandParameterManager parameterManager;

	public CommandBase(String service, String name, String description,
					   CommandParameterManager commandParameterManager) {
		this.service = service;
		this.name = name;
		this.description = description;
		this.parameterManager = commandParameterManager;
	}

	@Override
	public String getHelp() {
		StringBuilder sb = new StringBuilder(this.getDescription());
		sb.append(" ");
		sb.append("Usage: ");
		sb.append(this.getUsage());

		return sb.toString();
	}

	@Override
	public String getUsage() {
		final StringBuilder sb = new StringBuilder(CommandProperties.SERVICE_PREFIX + this.getService() +
				CommandProperties.COMMAND_SEPARATOR + this.name);
		final CommandParameterManager manager = this.getParameterManager();
		final Map<String, CommandParameter<? extends Comparable<?>>> params = manager.getParameters();

		if (params.isEmpty()) return sb.toString();

		final String paramUsage = params.values().stream().map(cp -> "<" + cp.getName() + ">").collect(Collectors.joining(CommandProperties.PARAM_SEPARATOR));
		sb.append(" ");
		sb.append(paramUsage);

		return sb.toString();
	}

	@Override
	public boolean matches(CommandInvocation ci) {
		final String service = ci.getService();
		final String command = ci.getCommand();

		if (!(service.equals(this.service) && command.equals(this.name))) return false;

		final List<String> givenParams = ci.getParams();

		return givenParams.size() == this.parameterManager.getParameters().size();
	}

	@Override
	public void onNext(Subject subj, Object obj) {
		if (obj instanceof ServicedChannelMessage) {
			final ServicedChannelMessage scm = (ServicedChannelMessage) obj;
			final CommandInvocation ci = scm.getCommandInvocation();

			if (this.getParameterManager().getParameters().size() != ci.getParams().size()) {
				final String usage = "Usage: " + this.getUsage();

				scm.getEvent().sendReply(usage);

				return;
			}

			if (!this.matches(ci)) return;

			this.handleServicedChannelMessage(scm);
		} else if (obj instanceof ServicedPrivateMessage) {
			final ServicedPrivateMessage spm = (ServicedPrivateMessage) obj;
			final CommandInvocation ci = spm.getCommandInvocation();

			if (this.getParameterManager().getParameters().size() != ci.getParams().size()) {
				final String usage = "Usage: " + this.getUsage();

				spm.getEvent().sendReply(usage);

				return;
			}

			if (!this.matches(ci)) return;

			this.handleServicedPrivateMessage(spm);
		}
	}
}
