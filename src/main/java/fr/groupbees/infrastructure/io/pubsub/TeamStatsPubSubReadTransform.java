package fr.groupbees.infrastructure.io.pubsub;

import fr.groupbees.application.PipelineConf;
import fr.groupbees.domain.TeamStatsRaw;
import fr.groupbees.infrastructure.io.jsonfile.JsonUtil;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import static org.apache.beam.sdk.values.TypeDescriptor.of;

public class TeamStatsPubSubReadTransform extends PTransform<PBegin, PCollection<TeamStatsRaw>> {

    private final PipelineConf pipelineConf;

    public TeamStatsPubSubReadTransform(PipelineConf pipelineConf) {
        this.pipelineConf = pipelineConf;
    }

    @Override
    public PCollection<TeamStatsRaw> expand(PBegin input) {
        return input
                .apply("Read Json file", PubsubIO.readStrings().fromSubscription(pipelineConf.getInputSubscription()))
                .apply("Deserialize", MapElements.into(of(TeamStatsRaw.class)).via(this::deserializeToTeamStats));
    }

    private TeamStatsRaw deserializeToTeamStats(final String teamStatsAsString) {
        return JsonUtil.deserialize(teamStatsAsString, TeamStatsRaw.class);
    }
}
