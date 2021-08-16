package xyz.vppiet.hellu;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

final class MiscHelloCommand extends ChannelCommand {
	public MiscHelloCommand(String service, String name, String description) {
		super(service, name, description);
	}

	@Override
	public void handleChannelMessageEvent(ChannelMessageEvent event) {
		String user = event.getActor().getNick();
		event.sendReply("Hello, " + user + "!");
	}
}
