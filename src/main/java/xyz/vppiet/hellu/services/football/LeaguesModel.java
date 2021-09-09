package xyz.vppiet.hellu.services.football;

import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import org.jetbrains.annotations.Nullable;
import xyz.vppiet.hellu.JsonModel;

import java.util.List;

@GenerateTypeAdapter
@AutoValue
abstract class LeaguesModel implements JsonModel {

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

	abstract Object errors();
	abstract String get();
	abstract Paging paging();
	abstract Parameters parameters();
	abstract List<Response> response();

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Paging {

		static Paging create(int current, int total) {
			return new AutoValue_LeaguesModel_Paging(current, total);
		}

		abstract int current();
		abstract int total();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Parameters {

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

		@Nullable abstract Integer id();
		@Nullable abstract String name();
		@Nullable abstract String country();
		@Nullable abstract String code();
		@Nullable abstract Integer season();
		@Nullable abstract Integer team();
		@Nullable abstract String type();
		@Nullable abstract String current();
		@Nullable abstract String search();
		@Nullable abstract Integer last();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Response {

		static Response create(Country country, League league, List<Season> seasons) {
			return new AutoValue_LeaguesModel_Response(country, league, seasons);
		}

		abstract Country country();
		abstract League league();
		abstract List<Season> seasons();

		@GenerateTypeAdapter
		@AutoValue
		abstract static class Country {

			static Country create(String code, String flag, String name) {
				return new AutoValue_LeaguesModel_Response_Country(code, flag, name);
			}

			abstract String code();
			abstract String flag();
			abstract String name();
		}

		@GenerateTypeAdapter
		@AutoValue
		abstract static class League {

			static League create(int id, String logo, String name, String type) {
				return new AutoValue_LeaguesModel_Response_League(id, logo, name, type);
			}

			abstract int id();
			abstract String logo();
			abstract String name();
			abstract String type();
		}

		@GenerateTypeAdapter
		@AutoValue
		abstract static class Season {

			static Season create(Coverage coverage, boolean current, String end, String start, int year) {
				return new AutoValue_LeaguesModel_Response_Season(coverage, current, end, start, year);
			}

			abstract Coverage coverage();
			abstract boolean current();
			abstract String end();
			abstract String start();
			abstract int year();

			@GenerateTypeAdapter
			@AutoValue
			abstract static class Coverage {

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

				abstract Fixtures fixtures();
				abstract boolean injuries();
				abstract boolean odds();
				abstract boolean players();
				abstract boolean predictions();
				abstract boolean standings();
				abstract boolean top_assists();
				abstract boolean top_cards();
				abstract boolean top_scorers();

				@GenerateTypeAdapter
				@AutoValue
				abstract static class Fixtures {

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

					abstract boolean events();
					abstract boolean lineups();
					abstract boolean statistics_fixtures();
					abstract boolean statistics_players();
				}
			}
		}
	}
}
