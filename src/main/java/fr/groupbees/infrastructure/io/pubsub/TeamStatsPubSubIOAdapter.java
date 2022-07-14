package fr.groupbees.infrastructure.io.pubsub;

import fr.groupbees.application.PipelineConf;
import fr.groupbees.domain.TeamStatsRaw;
import fr.groupbees.domain_ptransform.TeamStatsTopicIOConnector;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;

public class TeamStatsPubSubIOAdapter implements TeamStatsTopicIOConnector {

    private final PipelineConf pipelineConf;

    @Inject
    public TeamStatsPubSubIOAdapter(PipelineConf pipelineConf) {
        this.pipelineConf = pipelineConf;
    }

    @Override
    public PTransform<PBegin, PCollection<TeamStatsRaw>> read() {
        return new TeamStatsPubSubReadTransform(pipelineConf);
    }
}
