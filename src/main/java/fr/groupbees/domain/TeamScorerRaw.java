package fr.groupbees.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TeamScorerRaw implements Serializable {

    private String scorerFirstName;
    private String scorerLastName;
    private int goalsNumber;
    private int gamesNumber;
}
