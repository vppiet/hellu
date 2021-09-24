package xyz.vppiet.hellu.external.apifootball.models;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@GenerateTypeAdapter
@AutoValue
public abstract class FixturesModel {

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

	public abstract String get();
	public abstract Parameters parameters();
	public abstract Object errors();
	public abstract Integer results();
	public abstract Paging paging();
	public abstract List<Response> response();

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Parameters {

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

		@Nullable public abstract Integer id();
		@Nullable public abstract String live();
		@Nullable public abstract String date();
		@Nullable public abstract Integer league();
		@Nullable public abstract Integer season();
		@Nullable public abstract Integer team();
		@Nullable public abstract Integer last();
		@Nullable public abstract Integer next();
		@Nullable public abstract String from();
		@Nullable public abstract String to();
		@Nullable public abstract String status();
		@Nullable public abstract String timezone();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Paging {

		static Paging create(int current, int total) {
			return new AutoValue_FixturesModel_Paging(current, total);
		}

		public abstract int current();
		public abstract int total();
	}

	@GenerateTypeAdapter
	@AutoValue
	public abstract static class Response {

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

		public abstract Fixture fixture();
		public abstract League league();
		public abstract Teams teams();
		public abstract Goals goals();
		public abstract Score score();

		@GenerateTypeAdapter
		@AutoValue
		public abstract static class Fixture {

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

			public abstract int id();
			@Nullable public abstract String referee();
			public abstract String timezone();
			public abstract String date();
			public abstract int timestamp();
			public abstract Periods periods();
			public abstract Venue venue();
			public abstract Status status();

			@GenerateTypeAdapter
			@AutoValue
			public abstract static class Periods {

				static Periods create(@Nullable Integer first, @Nullable Integer second) {
					return new AutoValue_FixturesModel_Response_Fixture_Periods(first, second);
				}

				@Nullable public abstract Integer first();
				@Nullable public abstract Integer second();
			}

			@GenerateTypeAdapter
			@AutoValue
			public abstract static class Venue {

				static Venue create(int id, String name, String city) {
					return new AutoValue_FixturesModel_Response_Fixture_Venue(id, name, city);
				}

				public abstract int id();
				public abstract String name();
				public abstract String city();
			}

			@GenerateTypeAdapter
			@AutoValue
			public abstract static class Status {

				static Status create(String _long, String _short, int elapsed) {
					return new AutoValue_FixturesModel_Response_Fixture_Status(_long, _short, elapsed);
				}

				@SerializedName("long") public abstract String _long();
				@SerializedName("short") public abstract String _short();
				public abstract int elapsed();
			}
		}

		@GenerateTypeAdapter
		@AutoValue
		public abstract static class League {

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

			public abstract int id();
			public abstract String name();
			public abstract String country();
			public abstract String logo();
			public abstract String flag();
			public abstract int season();
			public abstract String round();
		}

		@GenerateTypeAdapter
		@AutoValue
		public abstract static class Teams {

			static Teams create(Team home, Team away) {
				return new AutoValue_FixturesModel_Response_Teams(home, away);
			}

			public abstract Team home();
			public abstract Team away();

			@GenerateTypeAdapter
			@AutoValue
			public abstract static class Team {

				static Team create(int id, String name, String logo, @Nullable Boolean winner) {
					return new AutoValue_FixturesModel_Response_Teams_Team(id, name, logo, winner);
				}

				public abstract int id();
				public abstract String name();
				public abstract String logo();
				@Nullable public abstract Boolean winner();
			}
		}

		@GenerateTypeAdapter
		@AutoValue
		public abstract static class Goals {

			static Goals create(int home, int away) {
				return new AutoValue_FixturesModel_Response_Goals(home, away);
			}

			public abstract int home();
			public abstract int away();
		}

		@GenerateTypeAdapter
		@AutoValue
		public abstract static class Score {

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

			public abstract ScoreSides halftime();
			public abstract ScoreSides fulltime();
			public abstract ScoreSides extratime();
			public abstract ScoreSides penalty();

			@GenerateTypeAdapter
			@AutoValue
			public abstract static class ScoreSides {

				static ScoreSides create(@Nullable Integer home, @Nullable Integer away) {
					return new AutoValue_FixturesModel_Response_Score_ScoreSides(home, away);
				}

				@Nullable public abstract Integer home();
				@Nullable public abstract Integer away();
			}
		}
	}

	public final String formatted() {
		if (this.response().isEmpty()) {
			return "No fixtures found.";
		}

		TreeMap<String, List<Response>> leagues = this.response().stream()
				.collect(Collectors.groupingBy(response -> response.league().name(), TreeMap::new, Collectors.toList()));

		return leagues.entrySet().stream().map(entry -> {
			StringBuilder formattedLeague = new StringBuilder();

			String league = entry.getKey();
			formattedLeague.append(league).append(": ");

			List<Response> responses = entry.getValue();
			String formattedLeagueResponses = responses.stream()
					.map(response -> {
						String homeTeam = response.teams().home().name();
						String awayTeam = response.teams().away().name();
						int elapsed = response.fixture().status().elapsed();
						int homeTeamScore = response.goals().home();
						int awayTeamScore = response.goals().away();

						return new StringBuilder().append(homeTeam)
								.append(" - ")
								.append(awayTeam)
								.append(" ")
								.append(elapsed)
								.append("' ")
								.append(homeTeamScore)
								.append("-")
								.append(awayTeamScore)
								.toString();
					})
					.collect(Collectors.joining(", "));
			formattedLeague.append(formattedLeagueResponses);

			return formattedLeague.toString();
		}).collect(Collectors.joining(" "));
	}
}
