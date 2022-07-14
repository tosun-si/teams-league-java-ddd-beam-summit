package fr.groupbees.infrastructure.io.csvfile;

import fr.groupbees.application.PipelineConf;
import fr.groupbees.domain.TeamStatsRaw;
import fr.groupbees.domain_ptransform.TeamStatsFileIOConnector;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;

public class TeamStatsCsvFileIOAdapter implements TeamStatsFileIOConnector {

    private final PipelineConf pipelineConf;

    @Inject
    public TeamStatsCsvFileIOAdapter(PipelineConf pipelineConf) {
        this.pipelineConf = pipelineConf;
    }

    @Override
    public PTransform<PBegin, PCollection<TeamStatsRaw>> read() {
        return new TeamStatsCsvFileReadTransform<TeamStatsRaw>(
                pipelineConf.getInputJsonFile(),
                pipelineConf.getInputFileSeparator()
        );
    }
}
