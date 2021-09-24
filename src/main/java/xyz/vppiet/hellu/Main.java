package xyz.vppiet.hellu;

import lombok.extern.log4j.Log4j2;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;

import xyz.vppiet.hellu.eventlisteners.ChannelMessageListener;
import xyz.vppiet.hellu.eventlisteners.EventListener;
import xyz.vppiet.hellu.eventlisteners.PrivateMessageListener;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.admin.AdminService;
import xyz.vppiet.hellu.services.football.FootballService;
import xyz.vppiet.hellu.services.football.LiveCommand;
import xyz.vppiet.hellu.services.help.HelpService;
import xyz.vppiet.hellu.services.help.ServicesCommand;
import xyz.vppiet.hellu.services.misc.CoordinatesCommand;
import xyz.vppiet.hellu.services.misc.HelloCommand;
import xyz.vppiet.hellu.services.misc.MiscService;
import xyz.vppiet.hellu.services.admin.QuitCommand;
import xyz.vppiet.hellu.services.misc.SlapCommand;
import xyz.vppiet.hellu.services.weather.CurrentWeatherCommand;
import xyz.vppiet.hellu.services.weather.WeatherService;

import java.io.File;
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

		// SERVICE: ADMIN
		Service adminService = new AdminService()
				.addCommand(new QuitCommand());

		// SERVICE: MISC
		Service miscService = new MiscService()
				.addCommand(new HelloCommand())
				.addCommand(new SlapCommand())
				.addCommand(new CoordinatesCommand());

		// SERVICE: HELP
		Service helpService = new HelpService()
				.addCommand(new ServicesCommand());

		// SERVICE: WEATHER
		Service weatherService = new WeatherService()
				.addCommand(new CurrentWeatherCommand());

		// SERVICE: FOOTBALL
		Service footballService = new FootballService()
				.addCommand(new LiveCommand());

		// SERVICE MANAGEMENT
		hellu.getServiceManager()
				.addService(adminService)
				.addService(miscService)
				.addService(helpService)
				.addService(weatherService)
				.addService(footballService);

		// RUN
		hellu.addChannel("#hellu");
		hellu.addChannel("#valioliiga");

		// DEBUG
		hellu.connect();
		//hellu.disconnect();
		//deleteFile("hellu.db");
	}

	private static void deleteFile(String name) {
		File file = new File(name);
		if (file.delete()) {
			log.info("{} deleted", file.getName());
		} else {
			log.info("{} not deleted", file.getName());
		}
	}
}
