package fr.groupbees.domain;

import com.google.common.collect.ImmutableMap;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class TeamStats implements Serializable {

    private static final Map<String, String> TEAM_SLOGANS = ImmutableMap.of(
            "PSG", "Paris est magique",
            "Real", "Hala Madrid"
    );

    private String teamName;
    private int teamScore;
    private int teamTotalGoals;
    private String teamSlogan;
    private TeamTopScorerStats topScorerStats;
    private TeamBestPasserStats bestPasserStats;

    public static TeamStats computeTeamStats(final TeamStatsRaw teamStatsRaw) {
        final List<TeamScorerRaw> teamScorers = teamStatsRaw.getScorers();

        final TeamScorerRaw topScorer = teamScorers.stream()
                .max(Comparator.comparing(TeamScorerRaw::getGoals))
                .orElseThrow(NoSuchElementException::new);

        final TeamScorerRaw bestPasser = teamScorers.stream()
                .max(Comparator.comparing(TeamScorerRaw::getGoalAssists))
                .orElseThrow(NoSuchElementException::new);

        final int teamTotalGoals = teamScorers.stream()
                .mapToInt(TeamScorerRaw::getGoals)
                .sum();

        val topScorerStats = TeamTopScorerStats.builder()
                .firstName(topScorer.getScorerFirstName())
                .lastName(topScorer.getScorerLastName())
                .goals(topScorer.getGoals())
                .games(topScorer.getGames())
                .build();

        val bestPasserStats = TeamBestPasserStats.builder()
                .firstName(bestPasser.getScorerFirstName())
                .lastName(bestPasser.getScorerLastName())
                .goalAssists(bestPasser.getGoalAssists())
                .games(bestPasser.getGames())
                .build();

        return TeamStats.builder()
                .teamName(teamStatsRaw.getTeamName())
                .teamScore(teamStatsRaw.getTeamScore())
                .teamTotalGoals(teamTotalGoals)
                .topScorerStats(topScorerStats)
                .bestPasserStats(bestPasserStats)
                .build();
    }

    public TeamStats addSloganToStats() {
        final String slogan = Optional
                .ofNullable(TEAM_SLOGANS.get(teamName))
                .orElseThrow(() -> new IllegalArgumentException("No slogan for team : " + teamName));

        return this
                .toBuilder()
                .teamSlogan(slogan)
                .build();
    }
}
