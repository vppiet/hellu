package xyz.vppiet.hellu.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Getter(AccessLevel.PUBLIC)
@Log4j2
public final class CommandParameterManager {
	private final Map<String, CommandParameter<? extends Comparable<?>>> parameters;

	public CommandParameterManager() {
		this.parameters = Collections.synchronizedSortedMap(new TreeMap<>());
	}

	@SafeVarargs
	public CommandParameterManager(CommandParameter<? extends Comparable<?>>... commandParameters) {
		this();

		synchronized (this.parameters) {
			for (CommandParameter<? extends Comparable<?>> cp : commandParameters) {
				this.parameters.put(cp.getName(), cp);
			}
		}
	}

	public <T extends Comparable<T>> void add(CommandParameter<T> c) {
		this.parameters.put(c.getName(), c);
	}

	public void setValue(String parameter, String value) {
		if (!this.parameters.containsKey(parameter)) {
			throw new IllegalArgumentException("Parameter doesn't exist");
		}

		CommandParameter<? extends Comparable<?>> param = this.parameters.get(parameter);

		if (param instanceof StringCommandParameter) {
			StringCommandParameter stringParam = (StringCommandParameter) param;
			stringParam.setValue(value);
		} else if (param instanceof NumericCommandParameter) {
			NumericCommandParameter numericParam = (NumericCommandParameter) param;
			numericParam.setValue(new BigDecimal(value));
		}
	}
}
