package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;

import xyz.vppiet.hellu.eventlisteners.ChannelMessageListener;
import xyz.vppiet.hellu.eventlisteners.EventListener;
import xyz.vppiet.hellu.eventlisteners.PrivateMessageListener;
import xyz.vppiet.hellu.services.Command;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.misc.HelloCommand;
import xyz.vppiet.hellu.services.misc.MiscService;
import xyz.vppiet.hellu.services.misc.QuitCommand;

import java.io.IOException;

@Log4j2
public class Main {
	public static void main(String[] args) throws IOException {

		// HELLU
		HelluSettings settings = HelluSettings.load("hellu.properties");
		Hellu hellu = new Hellu(settings);


		// LISTENERS
		EventListener<ChannelMessageEvent> channelMsgListener = new ChannelMessageListener();
		channelMsgListener.addHellu(hellu);

		EventListener<PrivateMessageEvent> privateMsgListener = new PrivateMessageListener();
		privateMsgListener.addHellu(hellu);


		// SERVICE: MISC
		Service miscService = new MiscService();

		// SERVICE: MISC, COMMAND: HELLO
		Command helloCommand = new HelloCommand();
		miscService.addCommand(helloCommand);

		// SERVICE: MISC, COMMAND: QUIT
		Command quitCommand = new QuitCommand();
		miscService.addCommand(quitCommand);

		// SERVICE MANAGEMENT
		ServiceManager serviceManager = hellu.getServiceManager();
		serviceManager.addService(miscService);


		// RUN
		hellu.addChannel("#hellu");
		hellu.connect();
	}
}
