package fr.groupbees.domain_transform;

import fr.groupbees.domain.TeamStats;
import fr.groupbees.domain.TeamStatsRaw;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;

import static org.apache.beam.sdk.values.TypeDescriptor.of;

public class TeamStatsTransform extends PTransform<PCollection<TeamStatsRaw>, PCollection<TeamStats>> {

    @Override
    public PCollection<TeamStats> expand(PCollection<TeamStatsRaw> input) {
        return input
                .apply("Validate fields", MapElements.into(of(TeamStatsRaw.class)).via(TeamStatsRaw::validateFields))
                .apply("Compute team stats", MapElements.into(of(TeamStats.class)).via(TeamStats::computeTeamStats))
                .apply("Add team slogan", MapElements.into(of(TeamStats.class)).via(TeamStats::addSloganToStats));
    }
}
