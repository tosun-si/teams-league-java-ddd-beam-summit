package fr.groupbees.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class TeamScoreStats implements Serializable {

    private String topScorerFirstName;
    private String topScorerLastName;
    private int topScorerGoalsNumber;
    private int topScorerGamesNumber;
    private int totalScoreNumber;
}
