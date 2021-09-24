package xyz.vppiet.hellu.services;

import com.google.auto.value.AutoValue;

import java.time.Instant;

@AutoValue
abstract class CommandMatchObservation {

	abstract String service();
	abstract String command();
	abstract Instant timestamp();

	static Builder builder() {
		return new AutoValue_CommandMatchObservation.Builder();
	}

	@AutoValue.Builder
	abstract static class Builder {
		abstract Builder setService(String service);
		abstract Builder setCommand(String command);
		abstract Builder setTimestamp(Instant timestamp);

		abstract CommandMatchObservation build();
	}
}
