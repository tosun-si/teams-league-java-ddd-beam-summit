package fr.groupbees.application;

import fr.groupbees.domain_transform.TeamStatsDatabaseIOConnector;
import fr.groupbees.domain_transform.TeamStatsFileIOConnector;
import fr.groupbees.domain_transform.TeamStatsInMemoryIOConnector;
import fr.groupbees.domain_transform.TeamStatsTransform;
import org.apache.beam.sdk.Pipeline;

import javax.inject.Inject;

public class TeamLeaguePipelineComposer {

    private final TeamStatsDatabaseIOConnector databaseIOConnector;
    private final TeamStatsFileIOConnector fileIOConnector;
    private final TeamStatsInMemoryIOConnector inMemoryIOConnector;

    @Inject
    public TeamLeaguePipelineComposer(TeamStatsDatabaseIOConnector databaseIOConnector,
                                      TeamStatsFileIOConnector fileIOConnector,
                                      TeamStatsInMemoryIOConnector inMemoryIOConnector) {
        this.databaseIOConnector = databaseIOConnector;
        this.fileIOConnector = fileIOConnector;
        this.inMemoryIOConnector = inMemoryIOConnector;
    }

    public Pipeline compose(final Pipeline pipeline) {
        pipeline.apply("Read team stats", inMemoryIOConnector.read())
                .apply("name", new TeamStatsTransform())
                .apply("Write to database", databaseIOConnector.write());

        return pipeline;
    }
}
