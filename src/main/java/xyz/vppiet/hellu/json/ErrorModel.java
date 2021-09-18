package xyz.vppiet.hellu.json;

public class ErrorModel implements DataModel {

	@Override
	public String formatted() {
		return "Something went wrong while either fetching or processing the data.";
	}
}
