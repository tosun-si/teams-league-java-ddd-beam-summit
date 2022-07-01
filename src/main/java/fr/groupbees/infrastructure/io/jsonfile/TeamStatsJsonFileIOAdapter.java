package fr.groupbees.infrastructure.io.jsonfile;

import fr.groupbees.application.TeamLeagueOptions;
import fr.groupbees.domain_transform.TeamStatsFileIOConnector;
import fr.groupbees.domain.TeamStatsRaw;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;

public class TeamStatsJsonFileIOAdapter implements TeamStatsFileIOConnector {

    private final TeamLeagueOptions options;

    @Inject
    public TeamStatsJsonFileIOAdapter(TeamLeagueOptions options) {
        this.options = options;
    }

    @Override
    public PTransform<PBegin, PCollection<TeamStatsRaw>> read() {
        return new TeamStatsJsonFileReadTransform(options);
    }
}
