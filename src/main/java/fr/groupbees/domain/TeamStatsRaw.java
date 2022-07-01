package fr.groupbees.domain;

import fr.groupbees.domain.exception.TeamStatsRawValidatorException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.isNull;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TeamStatsRaw implements Serializable {

    private String teamName;
    private int teamScore;
    private List<TeamScorerRaw> scorers;

    public static TeamStatsRaw validateFields(final TeamStatsRaw teamStatsRaw) {
        if (isNull(teamStatsRaw.getTeamName())) {
            throw new TeamStatsRawValidatorException("Team name cannot be null");
        }

        return teamStatsRaw;
    }
}
