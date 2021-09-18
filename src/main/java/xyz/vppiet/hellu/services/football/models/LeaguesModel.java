package xyz.vppiet.hellu.services.football.models;

import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import org.jetbrains.annotations.Nullable;
import xyz.vppiet.hellu.json.DataModel;

import java.util.List;

@GenerateTypeAdapter
@AutoValue
public abstract class LeaguesModel implements DataModel {

	static LeaguesModel create(
			Object errors,
			String get,
			Paging paging,
			Parameters parameters,
			List<Response> response) {
		return new AutoValue_LeaguesModel(
				errors,
				get,
				paging,
				parameters,
				response
		);
	}

	public abstract Object errors();
	public abstract String get();
	public abstract Paging paging();
	public abstract Parameters parameters();
	public abstract List<Response> response();

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Paging {

		static Paging create(int current, int total) {
			return new AutoValue_LeaguesModel_Paging(current, total);
		}

		public abstract int current();
		public abstract int total();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Parameters {

		static Parameters create(
				@Nullable Integer id,
				@Nullable String name,
				@Nullable String country,
				@Nullable String code,
				@Nullable Integer season,
				@Nullable Integer team,
				@Nullable String type,
				@Nullable String current,
				@Nullable String search,
				@Nullable Integer last) {
			return new AutoValue_LeaguesModel_Parameters(
					id,
					name,
					country,
					code,
					season,
					team,
					type,
					current,
					search,
					last
			);
		}

		@Nullable public abstract Integer id();
		@Nullable public abstract String name();
		@Nullable public abstract String country();
		@Nullable public abstract String code();
		@Nullable public abstract Integer season();
		@Nullable public abstract Integer team();
		@Nullable public abstract String type();
		@Nullable public abstract String current();
		@Nullable public abstract String search();
		@Nullable public abstract Integer last();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Response {

		static Response create(Country country, League league, List<Season> seasons) {
			return new AutoValue_LeaguesModel_Response(country, league, seasons);
		}

		public abstract Country country();
		public abstract League league();
		public abstract List<Season> seasons();

		@GenerateTypeAdapter
		@AutoValue
		public abstract static class Country {

			static Country create(String code, String flag, String name) {
				return new AutoValue_LeaguesModel_Response_Country(code, flag, name);
			}

			public abstract String code();
			public abstract String flag();
			public abstract String name();
		}

		@GenerateTypeAdapter
		@AutoValue
		public abstract static class League {

			static League create(int id, String logo, String name, String type) {
				return new AutoValue_LeaguesModel_Response_League(id, logo, name, type);
			}

			public abstract int id();
			public abstract String logo();
			public abstract String name();
			public abstract String type();
		}

		@GenerateTypeAdapter
		@AutoValue
		public abstract static class Season {

			static Season create(Coverage coverage, boolean current, String end, String start, int year) {
				return new AutoValue_LeaguesModel_Response_Season(coverage, current, end, start, year);
			}

			public abstract Coverage coverage();
			public abstract boolean current();
			public abstract String end();
			public abstract String start();
			public abstract int year();

			@GenerateTypeAdapter
			@AutoValue
			public abstract static class Coverage {

				static Coverage create(
						Fixtures fixtures,
						boolean injuries,
						boolean odds,
						boolean players,
						boolean predictions,
						boolean standings,
						boolean top_assists,
						boolean top_cards,
						boolean top_scorers) {
					return new AutoValue_LeaguesModel_Response_Season_Coverage(
							fixtures,
							injuries,
							odds,
							players,
							predictions,
							standings,
							top_assists,
							top_cards,
							top_scorers
					);
				}

				public abstract Fixtures fixtures();
				public abstract boolean injuries();
				public abstract boolean odds();
				public abstract boolean players();
				public abstract boolean predictions();
				public abstract boolean standings();
				public abstract boolean top_assists();
				public abstract boolean top_cards();
				public abstract boolean top_scorers();

				@GenerateTypeAdapter
				@AutoValue
				public abstract static class Fixtures {

					static Fixtures create(
							boolean events,
							boolean lineups,
							boolean statistics_fixtures,
							boolean statistics_players) {
						return new AutoValue_LeaguesModel_Response_Season_Coverage_Fixtures(
								events,
								lineups,
								statistics_fixtures,
								statistics_players
						);
					}

					public abstract boolean events();
					public abstract boolean lineups();
					public abstract boolean statistics_fixtures();
					public abstract boolean statistics_players();
				}
			}
		}
	}

	@Override
	public final String formatted() {
		return this.toString();
	}
}
