package xyz.vppiet.hellu.services.misc;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import xyz.vppiet.hellu.Subject;
import xyz.vppiet.hellu.services.CommandInvoke;
import xyz.vppiet.hellu.services.ServiceBase;

@Log4j2
@ToString(callSuper = true)
public class MiscService extends ServiceBase {

	private static final String NAME = "misc";
	private static final String DESCRIPTION = "Miscellaneous commands";

	public MiscService() {
		super(NAME, DESCRIPTION);
	}

	@Override
	public void onNext(Subject sub, Object o) {
		if (o instanceof CommandInvoke) {
			CommandInvoke ci = (CommandInvoke) o;
			this.handleCommandInvoke(sub, ci);
		}
	}

	@Override
	public void handleCommandInvoke(Subject sub, CommandInvoke ci) {
		String service = ci.getService();
		if (!service.equals(this.name)) return;

		log.info("Call to: {}", this);

		this.notifyObservers(sub, ci);
	}
}
