package xyz.vppiet.hellu.services.weather;

import xyz.vppiet.hellu.services.CommandBase;
import xyz.vppiet.hellu.services.CommandParameterManager;
import xyz.vppiet.hellu.services.Service;
import xyz.vppiet.hellu.services.ServicedChannelMessage;
import xyz.vppiet.hellu.services.ServicedPrivateMessage;
import xyz.vppiet.hellu.services.StringCommandParameter;

public class CurrentConditionsCommand extends CommandBase {

	private static final String SERVICE = "weather";
	private static final String NAME = "current";
	private static final String DESCRIPTION = "Gets current weather for provided city";
	private static final CommandParameterManager PARAMS = new CommandParameterManager(
			new StringCommandParameter("city", "")
	);

	public CurrentConditionsCommand() {
		super(SERVICE, NAME, DESCRIPTION, PARAMS);
	}

	@Override
	public void handleServicedChannelMessage(ServicedChannelMessage scm) {
		Service sourceService = scm.getSourceService();

		if (!(sourceService instanceof WeatherService)) return;

		WeatherService weatherService = (WeatherService) sourceService;
		String city = scm.getCommandInvocation().getParams().get(0);
		String weather = weatherService.getOpenWeatherMap().getCurrentConditionsByCityFormatted(city);

		scm.getEvent().sendReply(weather);
	}

	@Override
	public void handleServicedPrivateMessage(ServicedPrivateMessage spm) {
		Service sourceService = spm.getSourceService();

		if (!(sourceService instanceof WeatherService)) return;

		WeatherService weatherService = (WeatherService) sourceService;
		String city = spm.getCommandInvocation().getParams().get(0);
		String weather = weatherService.getOpenWeatherMap().getCurrentConditionsByCityFormatted(city);

		spm.getEvent().sendReply(weather);
	}
}
