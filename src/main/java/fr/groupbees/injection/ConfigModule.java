package fr.groupbees.injection;

import dagger.Module;
import dagger.Provides;
import fr.groupbees.application.TeamLeagueOptions;
import fr.groupbees.application.PipelineConf;

@Module
class ConfigModule {

    @Provides
    static PipelineConf providePipelineConf(TeamLeagueOptions options) {
        return PipelineConf
                .builder()
                .inputJsonFile(options.getInputJsonFile())
                .inputSubscription(options.getInputSubscription())
                .teamLeagueDataset(options.getTeamLeagueDataset())
                .teamStatsTable(options.getTeamStatsTable())
                .bqWriteMethod(options.getBqWriteMethod())
                .build();
    }
}
