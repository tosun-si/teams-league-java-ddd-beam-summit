package fr.groupbees.infrastructure.io.inmemory;

import fr.groupbees.domain.TeamScorerRaw;
import fr.groupbees.domain.TeamStatsRaw;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class TeamStatsInMemoryReadTransform extends PTransform<PBegin, PCollection<TeamStatsRaw>> {

    @Inject
    public TeamStatsInMemoryReadTransform() {
    }

    @Override
    public PCollection<TeamStatsRaw> expand(PBegin input) {
        final List<TeamScorerRaw> psgScorers = Arrays.asList(
                new TeamScorerRaw("Kylian", "Mbappe", 15, 13),
                new TeamScorerRaw("Sa Silva", "Neymar", 11, 12),
                new TeamScorerRaw("Angel", "Di Maria", 7, 13),
                new TeamScorerRaw("Lionel", "Messi", 12, 13),
                new TeamScorerRaw("Marco", "Verrati", 3, 13)
        );

        final List<TeamScorerRaw> realScorers = Arrays.asList(
                new TeamScorerRaw("Karim", "Benzema", 14, 13),
                new TeamScorerRaw("Junior", "Vinicius", 9, 12),
                new TeamScorerRaw("Luca", "Modric", 5, 11),
                new TeamScorerRaw("Silva", "Rodrygo", 7, 13),
                new TeamScorerRaw("Marco", "Asensio", 6, 13)
        );


        final List<TeamStatsRaw> teamStats = Arrays.asList(
                new TeamStatsRaw("PSG", 30, psgScorers),
                new TeamStatsRaw("Real", 25, realScorers)
        );

        return input.apply("Create in memory team stats", Create.of(teamStats));
    }
}
