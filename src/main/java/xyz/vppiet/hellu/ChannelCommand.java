package xyz.vppiet.hellu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.beans.PropertyChangeEvent;
import java.util.Objects;
import java.util.regex.Pattern;

@Log4j2
@ToString(onlyExplicitlyIncluded = true)
abstract class ChannelCommand implements Command<ChannelMessageEvent> {
	@ToString.Include
	private static final String PREFIX = ".";

	protected static final String PROP_IN_CHANNEL_MESSAGE_EVENT = "channelMessageEvent";

	@ToString.Include
	private final String service;

	@Getter(AccessLevel.PUBLIC)
	@ToString.Include
	private final String name;

	private final String description;
	private final Pattern filter;

	public ChannelCommand(String service, String name, String description) {
		this.service = service;
		this.name = name;
		this.description = description;
		this.filter = Pattern.compile("^\\" + PREFIX + service + " " + name, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (Objects.isNull(pce) || Objects.isNull(pce.getNewValue())) return;

		String propertyName = pce.getPropertyName();

		switch (propertyName) {
			case PROP_IN_CHANNEL_MESSAGE_EVENT:
				this.handleIncomingChannelMessageEvent(pce);
			default:
				log.warn("Property {} dismissed", propertyName);
		}
	}

	protected void handleIncomingChannelMessageEvent(PropertyChangeEvent pce) {
		Object newValue = pce.getNewValue();

		if (!(newValue instanceof ChannelMessageEvent)) return;

		ChannelMessageEvent event = (ChannelMessageEvent) newValue;
		String msg = event.getMessage();

		if (!this.matchesFilter(msg)) return;

		event.sendReply(this.getReply(event));
	}

	abstract String getReply(ChannelMessageEvent event);

	@Override
	public boolean matchesFilter(String msg) {
		return this.filter.matcher(msg).matches();
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
