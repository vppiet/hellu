package xyz.vppiet.hellu.services.weather;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.external.openweathermap.OpenWeatherMap;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.ParameterManager;
import xyz.vppiet.hellu.services.ServicedMessage;

import java.util.List;

public final class CurrentWeatherCommand extends CommandBase {
	private static final String SERVICE = "weather";
	private static final String NAME = "current";
	private static final String DESCRIPTION = "Gets current weather for provided city and country (optional).";
	private static final ParameterManager PARAMS = new ParameterManager().addRequired("city").addOptional("country");

	public CurrentWeatherCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedMessage(ServicedMessage sm) {
		List<String> params = sm.getCommandInvocation().getParams();
		String city = params.get(0);
		String country = "";

		if (params.size() > 1) {
			country = params.get(1);
		}

		ReplyableEvent event = sm.getReplyableEvent();
		this.replyWithCurrentWeather(event, city, country);
	}

	private void replyWithCurrentWeather(ReplyableEvent event, String city, String country) {
		String reply = OpenWeatherMap.getCurrentWeatherFormatted(city, country);
		event.sendReply(reply);
	}
}
