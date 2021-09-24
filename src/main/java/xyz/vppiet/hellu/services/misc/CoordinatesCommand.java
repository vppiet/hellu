package xyz.vppiet.hellu.services.misc;

import org.kitteh.irc.client.library.event.helper.ReplyableEvent;
import xyz.vppiet.hellu.external.openweathermap.OpenWeatherMap;
import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.ParameterManager;
import xyz.vppiet.hellu.services.ServicedMessage;

import java.util.List;

public class CoordinatesCommand extends CommandBase {

	private static final String SERVICE = "misc";
	private static final String NAME = "coordinates";
	private static final String DESCRIPTION = "Displays coordinates for a given place.";
	private static final ParameterManager PARAMS = new ParameterManager()
			.addRequired("city")
			.addOptional("country");

	public CoordinatesCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedMessage(ServicedMessage sm) {
		ReplyableEvent event = sm.getReplyableEvent();
		List<String> params = sm.getCommandInvocation().getParams();
		String city = params.get(0);

		if (params.size() > 1) {
			String country = params.get(1);
			this.replyWithGeoCoding(event, city, country);
		} else {
			this.replyWithGeoCoding(event, city, "");
		}
	}

	private void replyWithGeoCoding(ReplyableEvent event, String city, String country) {
		String reply = OpenWeatherMap.getGeoCodingFormatted(city, country);
		event.sendReply(reply);
	}
}
