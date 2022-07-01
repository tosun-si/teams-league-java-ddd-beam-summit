package fr.groupbees.infrastructure.io.pubsub;

import fr.groupbees.application.TeamLeagueOptions;
import fr.groupbees.domain.TeamStatsRaw;
import fr.groupbees.domain_transform.TeamStatsTopicIOConnector;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;

public class TeamStatsPubSubIOAdapter implements TeamStatsTopicIOConnector {

    private final TeamLeagueOptions options;

    @Inject
    public TeamStatsPubSubIOAdapter(TeamLeagueOptions options) {
        this.options = options;
    }

    @Override
    public PTransform<PBegin, PCollection<TeamStatsRaw>> read() {
        return new TeamStatsPubSubReadTransform(options);
    }
}
