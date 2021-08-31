package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import xyz.vppiet.hellu.CommandInvocation;
import xyz.vppiet.hellu.CommandProperties;
import xyz.vppiet.hellu.Subject;

import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Log4j2
@ToString
public abstract class CommandBase implements Command {

	protected final String service;
	protected final String name;
	protected final String description;
	protected final List<CommandParameter> params;
	protected final String usageDescription;

	public CommandBase(String service, String name, String description, List<CommandParameter> params) {
		this.service = service;
		this.name = name;
		this.description = description;
		this.params = params;
		this.usageDescription = CommandProperties.getUsageDescription(this.service, this.name, this.params);
	}

	@Override
	public boolean matches(CommandInvocation ci) {
		String service = ci.getService();
		String command = ci.getCommand();

		if (!(service.equals(this.service) && command.equals(this.name))) return false;

		List<String> givenParams = ci.getParams();

		return givenParams.size() == this.params.size();
	}

	@Override
	public void onNext(Subject subj, Object obj) {
		if (obj instanceof ServicedChannelMessage) {
			ServicedChannelMessage scm = (ServicedChannelMessage) obj;
			CommandInvocation ci = scm.getCommandInvocation();

			if (!this.matches(ci)) return;

			this.handleServicedChannelMessage(scm);
		} else if (obj instanceof ServicedPrivateMessage) {
			ServicedPrivateMessage spm = (ServicedPrivateMessage) obj;
			CommandInvocation ci = spm.getCommandInvoke();

			if (!this.matches(ci)) return;

			this.handleServicedPrivateMessage(spm);
		}
	}
}
