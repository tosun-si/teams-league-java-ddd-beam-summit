package fr.groupbees.application;

import fr.groupbees.domain_transform.*;
import org.apache.beam.sdk.Pipeline;

import javax.inject.Inject;

public class TeamLeaguePipelineComposer {

    private final TeamStatsDatabaseIOConnector databaseIOConnector;
    private final TeamStatsFileIOConnector fileIOConnector;
    private final TeamStatsInMemoryIOConnector inMemoryIOConnector;
    private final TeamStatsTopicIOConnector topicIOConnector;

    @Inject
    public TeamLeaguePipelineComposer(TeamStatsDatabaseIOConnector databaseIOConnector,
                                      TeamStatsFileIOConnector fileIOConnector,
                                      TeamStatsInMemoryIOConnector inMemoryIOConnector,
                                      TeamStatsTopicIOConnector topicIOConnector) {
        this.databaseIOConnector = databaseIOConnector;
        this.fileIOConnector = fileIOConnector;
        this.inMemoryIOConnector = inMemoryIOConnector;
        this.topicIOConnector = topicIOConnector;
    }

    public Pipeline compose(final Pipeline pipeline) {
        pipeline.apply("Read team stats", fileIOConnector.read())
                .apply("name", new TeamStatsTransform())
                .apply("Write to database", databaseIOConnector.write());

        return pipeline;
    }
}
