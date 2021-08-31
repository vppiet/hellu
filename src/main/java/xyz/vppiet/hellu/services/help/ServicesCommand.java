package xyz.vppiet.hellu.services.help;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;

import xyz.vppiet.hellu.ServiceManager;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ServicesCommand extends CommandBase {

	private static final String SERVICE = "help";
	private static final String NAME = "services";
	private static final String DESCRIPTION = "Shows all services.";

	public ServicesCommand() {
		super(SERVICE, NAME, DESCRIPTION, new ArrayList<>());
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {
		ChannelMessageEvent event = scm.getEvent();
		ServiceManager sm = scm.getSourceServiceManager();
		String services = getServiceNames(sm);

		this.replyWithServices(event, services);
	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {
		PrivateMessageEvent event = spm.getEvent();
		ServiceManager sm = spm.getSourceServiceManager();
		String services = getServiceNames(sm);

		this.replyWithServices(event, services);
	}

	static private String getServiceNames(ServiceManager sm) {
		return sm.getServices().stream()
				.map(Service::getName)
				.sorted()
				.collect(Collectors.joining(", "));
	}

	private void replyWithServices(ReplyableEvent event, String services) {
		event.sendReply("Services: " + services);
	}
}
