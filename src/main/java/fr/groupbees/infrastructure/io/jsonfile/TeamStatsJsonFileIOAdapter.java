package fr.groupbees.infrastructure.io.jsonfile;

import fr.groupbees.infrastructure.io.PipelineConf;
import fr.groupbees.domain.TeamStatsRaw;
import fr.groupbees.domain_transform.TeamStatsFileIOConnector;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;

public class TeamStatsJsonFileIOAdapter implements TeamStatsFileIOConnector {

    private final PipelineConf pipelineConf;

    @Inject
    public TeamStatsJsonFileIOAdapter(PipelineConf pipelineConf) {
        this.pipelineConf = pipelineConf;
    }

    @Override
    public PTransform<PBegin, PCollection<TeamStatsRaw>> read() {
        return new TeamStatsJsonFileReadTransform(pipelineConf);
    }
}
