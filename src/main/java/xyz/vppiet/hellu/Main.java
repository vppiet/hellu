package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;

import xyz.vppiet.hellu.eventlisteners.ChannelMessageListener;
import xyz.vppiet.hellu.eventlisteners.EventListener;
import xyz.vppiet.hellu.eventlisteners.PrivateMessageListener;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.help.HelpService;
import xyz.vppiet.hellu.services.help.ServicesCommand;
import xyz.vppiet.hellu.services.misc.HelloCommand;
import xyz.vppiet.hellu.services.misc.MiscService;
import xyz.vppiet.hellu.services.misc.QuitCommand;
import xyz.vppiet.hellu.services.misc.SlapCommand;

import java.io.IOException;

@Log4j2
public class Main {
	public static void main(String[] args) throws IOException {

		// HELLU
		HelluSettings settings = HelluSettings.load("hellu.properties");
		Hellu hellu = new Hellu(settings);


		// LISTENER: CHANNEL MESSAGE EVENT
		EventListener<ChannelMessageEvent> channelMsgListener = new ChannelMessageListener();
		channelMsgListener.addHellu(hellu);

		// LISTENER: PRIVATE MESSAGE EVENT
		EventListener<PrivateMessageEvent> privateMsgListener = new PrivateMessageListener();
		privateMsgListener.addHellu(hellu);


		// SERVICE: MISC
		Service miscService = new MiscService();
		miscService.addCommand(new HelloCommand());
		miscService.addCommand(new QuitCommand());
		miscService.addCommand(new SlapCommand());

		// SERVICE: HELP
		Service helpService = new HelpService();
		helpService.addCommand(new ServicesCommand());


		// SERVICE MANAGEMENT
		ServiceManager serviceManager = hellu.getServiceManager();
		serviceManager
				.addService(miscService)
				.addService(helpService);


		// RUN
		hellu.addChannel("#hellu");
		hellu.connect();
	}
}
