package xyz.vppiet.hellu.external.apifootball.models;

import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;

@AutoValue
@GenerateTypeAdapter
public abstract class CountriesModel {
	public abstract Object errors();
	public abstract String get();
	public abstract Paging paging();

	static Builder builder() {
		return new AutoValue_CountriesModel.Builder();
	}

	@AutoValue.Builder
	abstract static class Builder {
		public abstract Builder setErrors(Object value);
		public abstract Builder setGet(String value);
		public abstract Builder setPaging(Paging value);
		public abstract CountriesModel build();
	}

	@AutoValue
	@GenerateTypeAdapter
	abstract static class Paging {
		public abstract int current();
		public abstract int total();

		static Builder builder() {
			return new AutoValue_CountriesModel_Paging.Builder();
		}

		@AutoValue.Builder
		abstract static class Builder {
			public abstract Builder setCurrent(int value);
			public abstract Builder setTotal(int value);
			public abstract Paging build();
		}
	}
}
