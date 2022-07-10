package fr.groupbees.injection;

import dagger.Binds;
import dagger.Module;
import fr.groupbees.domain_transform.TeamStatsDatabaseIOConnector;
import fr.groupbees.domain_transform.TeamStatsFileIOConnector;
import fr.groupbees.domain_transform.TeamStatsInMemoryIOConnector;
import fr.groupbees.domain_transform.TeamStatsTopicIOConnector;
import fr.groupbees.infrastructure.io.bigquery.TeamStatsBigQueryIOAdapter;
import fr.groupbees.infrastructure.io.jsonfile.TeamStatsJsonFileIOAdapter;
import fr.groupbees.infrastructure.io.mock.TeamStatsMockIOAdapter;
import fr.groupbees.infrastructure.io.pubsub.TeamStatsPubSubIOAdapter;

@Module
public interface IOConnectorModule {

    @Binds
    TeamStatsDatabaseIOConnector provideTeamStatsDatabaseIOConnector(TeamStatsBigQueryIOAdapter bigQueryIOAdapter);

    @Binds
    TeamStatsFileIOConnector provideTeamStatsFileIOConnector(TeamStatsJsonFileIOAdapter fileIOAdapter);

    @Binds
    TeamStatsInMemoryIOConnector provideTeamStatsInMemoryIOConnector(TeamStatsMockIOAdapter inMemoryIOAdapter);

    @Binds
    TeamStatsTopicIOConnector provideTeamStatsTopicIOConnector(TeamStatsPubSubIOAdapter pubSubIOAdapter);
}
