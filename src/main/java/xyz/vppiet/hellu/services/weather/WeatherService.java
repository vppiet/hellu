package xyz.vppiet.hellu.services.weather;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import xyz.vppiet.hellu.services.ServiceBase;

@Getter(AccessLevel.PACKAGE)
@ToString(callSuper = true)
public final class WeatherService extends ServiceBase {

	private static final String NAME = "weather";
	private static final String DESCRIPTION = "Contains weather related commands.";

	public WeatherService() {
		super(NAME, DESCRIPTION);
	}
}
