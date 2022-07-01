package fr.groupbees.infrastructure.io.csvfile;

import fr.groupbees.application.TeamLeagueOptions;
import fr.groupbees.domain_transform.TeamStatsFileIOConnector;
import fr.groupbees.domain.TeamStatsRaw;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;

public class TeamStatsCsvFileIOAdapter implements TeamStatsFileIOConnector {

    private final TeamLeagueOptions options;

    @Inject
    public TeamStatsCsvFileIOAdapter(TeamLeagueOptions options) {
        this.options = options;
    }

    @Override
    public PTransform<PBegin, PCollection<TeamStatsRaw>> read() {
        return new TeamStatsCsvFileReadTransform<TeamStatsRaw>(options.getInputFile(), options.getInputFileSeparator());
    }
}
