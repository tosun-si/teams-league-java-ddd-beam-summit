package fr.groupbees.domain;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@ToString
public class TeamStats implements Serializable {

    private static final Map<String, String> TEAM_SLOGANS = ImmutableMap.of(
            "PSG", "Paris est magique",
            "Real", "Hala Madrid"
    );

    private String teamName;
    private int teamScore;
    private String teamSlogan;
    private TeamScoreStats scoreStats;

    public static TeamStats computeTeamStats(final TeamStatsRaw teamStatsRaw) {
        final List<TeamScorerRaw> teamScorers = teamStatsRaw.getScorers();

        final TeamScorerRaw topScorer = teamScorers.stream()
                .max(Comparator.comparing(TeamScorerRaw::getGoalsNumber))
                .orElseThrow(NoSuchElementException::new);

        final int totalScoreTeam = teamScorers.stream()
                .mapToInt(TeamScorerRaw::getGoalsNumber)
                .sum();

        final TeamScoreStats scoreStats = TeamScoreStats.builder()
                .topScorerFirstName(topScorer.getScorerFirstName())
                .topScorerLastName(topScorer.getScorerLastName())
                .topScorerGoalsNumber(topScorer.getGoalsNumber())
                .topScorerGoalsNumber(topScorer.getGamesNumber())
                .totalScoreNumber(totalScoreTeam)
                .build();

        return TeamStats.builder()
                .teamName(teamStatsRaw.getTeamName())
                .teamScore(teamStatsRaw.getTeamScore())
                .scoreStats(scoreStats)
                .build();
    }

    public static TeamStats addSloganToStats(final TeamStats teamStats) {
        final String slogan = Optional
                .ofNullable(TEAM_SLOGANS.get(teamStats.getTeamName()))
                .orElseThrow(() -> new IllegalArgumentException("No slogan for team : " + teamStats.getTeamName()));

        return teamStats
                .toBuilder()
                .teamSlogan(slogan)
                .build();
    }
}
