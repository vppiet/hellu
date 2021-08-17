package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.exception.KittehNagException;

import java.io.IOException;
import java.net.UnknownHostException;

@Log4j2
public final class Main {

	private static final String MISC_SERVICE = "misc";
	private static final String HELP_SERVICE = "help";

	private static final String PROP_CHANNEL_MESSAGE_EVENT = "channelMessageEvent";
	private static final String PROP_SERVICE_REGISTER = "serviceRegister";
	private static final String PROP_SERVICE_INFO = "serviceInfo";

	public static void main(String[] args) throws IOException {
		try {
			HelluSettings config = HelluSettings.load("hellu.properties");
			Hellu hellu = new Hellu(config);


			// CHANNEL MESSAGE LISTENER
			ChannelMessageListener channelMessageListener = new ChannelMessageListener();
			hellu.addListener(channelMessageListener);


			// SERVICE: HELP
			HelpService helpService = new HelpService(
					HELP_SERVICE,
					"All commands belong to a service. Show all services by issuing '.help services'." +
							" Show all commands of a service by issuing '.help <service>'." +
							" Show description of a command by issuing '.help <service> <command>'."
			);
			channelMessageListener.addPropertyChangeListener(PROP_CHANNEL_MESSAGE_EVENT, helpService);
			channelMessageListener.addPropertyChangeListener(PROP_SERVICE_REGISTER, helpService);
			helpService.addPropertyChangeListener(PROP_SERVICE_INFO, channelMessageListener);

			Command<ChannelMessageEvent> helpServicesCommand = new HelpServicesCommand(
					HELP_SERVICE,
					"services",
					"Shows all services."
			);
			helpService.addPropertyChangeListener(PROP_CHANNEL_MESSAGE_EVENT, helpServicesCommand);
			helpService.addPropertyChangeListener(PROP_SERVICE_REGISTER, helpServicesCommand);


			// SERVICE: MISC
			ChannelService miscService = new ChannelService(
					MISC_SERVICE,
					"Misc service contains an assortment of unrelated commands.");
			channelMessageListener.addPropertyChangeListener(PROP_CHANNEL_MESSAGE_EVENT, miscService);
			miscService.addPropertyChangeListener(PROP_SERVICE_INFO, channelMessageListener);

			Command<ChannelMessageEvent> miscHelloCommand = new MiscHelloCommand(
					MISC_SERVICE,
					"hello",
					"Says hello.");
			miscService.addPropertyChangeListener(PROP_CHANNEL_MESSAGE_EVENT, miscHelloCommand);

			hellu.connect();
			hellu.joinChannel("#valioliiga");
		} catch (UnknownHostException | KittehNagException ignored) {

		}
	}
}
