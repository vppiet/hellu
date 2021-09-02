package xyz.vppiet.hellu.services;

import java.math.BigDecimal;

public class NumericCommandParameter extends CommandParameter<BigDecimal> {

	public NumericCommandParameter(String name, BigDecimal value) {
		super(name, value);
	}
}
