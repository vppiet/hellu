package xyz.vppiet.hellu.services.football;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import org.jetbrains.annotations.Nullable;
import xyz.vppiet.hellu.JsonModel;

import java.util.List;

@GenerateTypeAdapter
@AutoValue
abstract class FixturesModel implements JsonModel {

	static FixturesModel create(
			String get,
			Parameters parameters,
			Object errors,
			Integer results,
			Paging paging,
			List<Response> response) {
		return new AutoValue_FixturesModel(
				get,
				parameters,
				errors,
				results,
				paging,
				response);
	}

	abstract String get();
	abstract Parameters parameters();
	abstract Object errors();
	abstract Integer results();
	abstract Paging paging();
	abstract List<Response> response();

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Parameters {

		static Parameters create(
				Integer id,
				String live,
				String date,
				Integer league,
				Integer season,
				Integer team,
				Integer last,
				Integer next,
				String from,
				String to,
				String status,
				String timezone) {
			return new AutoValue_FixturesModel_Parameters(
					id,
					live,
					date,
					league,
					season,
					team,
					last,
					next,
					from,
					to,
					status,
					timezone);
		}

		@Nullable abstract Integer id();
		@Nullable abstract String live();
		@Nullable abstract String date();
		@Nullable abstract Integer league();
		@Nullable abstract Integer season();
		@Nullable abstract Integer team();
		@Nullable abstract Integer last();
		@Nullable abstract Integer next();
		@Nullable abstract String from();
		@Nullable abstract String to();
		@Nullable abstract String status();
		@Nullable abstract String timezone();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Paging {

		static Paging create(int current, int total) {
			return new AutoValue_FixturesModel_Paging(current, total);
		}

		abstract int current();
		abstract int total();
	}

	@GenerateTypeAdapter
	@AutoValue
	abstract static class Response {

		static Response create(
				Fixture fixture,
				League league,
				Teams teams,
				Goals goals,
				Score score
		) {
			return new AutoValue_FixturesModel_Response(
					fixture,
					league,
					teams,
					goals,
					score
			);
		}

		abstract Fixture fixture();
		abstract League league();
		abstract Teams teams();
		abstract Goals goals();
		abstract Score score();

		@GenerateTypeAdapter
		@AutoValue
		abstract static class Fixture {

			static Fixture create(
					int id,
					@Nullable String referee,
					String timezone,
					String date,
					int timestamp,
					Periods periods,
					Venue venue,
					Status status) {
				return new AutoValue_FixturesModel_Response_Fixture(
						id,
						referee,
						timezone,
						date,
						timestamp,
						periods,
						venue,
						status
				);
			}

			abstract int id();
			@Nullable abstract String referee();
			abstract String timezone();
			abstract String date();
			abstract int timestamp();
			abstract Periods periods();
			abstract Venue venue();
			abstract Status status();

			@GenerateTypeAdapter
			@AutoValue
			abstract static class Periods {

				static Periods create(@Nullable Integer first, @Nullable Integer second) {
					return new AutoValue_FixturesModel_Response_Fixture_Periods(first, second);
				}

				@Nullable abstract Integer first();
				@Nullable abstract Integer second();
			}

			@GenerateTypeAdapter
			@AutoValue
			abstract static class Venue {

				static Venue create(int id, String name, String city) {
					return new AutoValue_FixturesModel_Response_Fixture_Venue(id, name, city);
				}

				abstract int id();
				abstract String name();
				abstract String city();
			}

			@GenerateTypeAdapter
			@AutoValue
			abstract static class Status {

				static Status create(String _long, String _short, int elapsed) {
					return new AutoValue_FixturesModel_Response_Fixture_Status(_long, _short, elapsed);
				}

				@SerializedName("long") abstract String _long();
				@SerializedName("short") abstract String _short();
				abstract int elapsed();
			}
		}

		@GenerateTypeAdapter
		@AutoValue
		abstract static class League {

			static League create(
					int id,
					String name,
					String country,
					String logo,
					String flag,
					int season,
					String round) {
				return new AutoValue_FixturesModel_Response_League(
						id,
						name,
						country,
						logo,
						flag,
						season,
						round);
			}

			abstract int id();
			abstract String name();
			abstract String country();
			abstract String logo();
			abstract String flag();
			abstract int season();
			abstract String round();
		}

		@GenerateTypeAdapter
		@AutoValue
		abstract static class Teams {

			static Teams create(Team home, Team away) {
				return new AutoValue_FixturesModel_Response_Teams(home, away);
			}

			abstract Team home();
			abstract Team away();

			@GenerateTypeAdapter
			@AutoValue
			abstract static class Team {

				static Team create(int id, String name, String logo, @Nullable Boolean winner) {
					return new AutoValue_FixturesModel_Response_Teams_Team(id, name, logo, winner);
				}

				abstract int id();
				abstract String name();
				abstract String logo();
				@Nullable abstract Boolean winner();
			}
		}

		@GenerateTypeAdapter
		@AutoValue
		abstract static class Goals {

			static Goals create(int home, int away) {
				return new AutoValue_FixturesModel_Response_Goals(home, away);
			}

			abstract int home();
			abstract int away();
		}

		@GenerateTypeAdapter
		@AutoValue
		abstract static class Score {

			static Score create(
					ScoreSides halftime,
					ScoreSides fulltime,
					ScoreSides extratime,
					ScoreSides penalty) {
				return new AutoValue_FixturesModel_Response_Score(
						halftime,
						fulltime,
						extratime,
						penalty);
			}

			abstract ScoreSides halftime();
			abstract ScoreSides fulltime();
			abstract ScoreSides extratime();
			abstract ScoreSides penalty();

			@GenerateTypeAdapter
			@AutoValue
			abstract static class ScoreSides {

				static ScoreSides create(@Nullable Integer home, @Nullable Integer away) {
					return new AutoValue_FixturesModel_Response_Score_ScoreSides(home, away);
				}

				@Nullable abstract Integer home();
				@Nullable abstract Integer away();
			}
		}
	}
}
