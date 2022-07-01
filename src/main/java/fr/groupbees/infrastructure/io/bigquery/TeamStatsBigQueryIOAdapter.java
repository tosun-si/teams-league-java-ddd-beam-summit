package fr.groupbees.infrastructure.io.bigquery;

import com.google.api.services.bigquery.model.TableRow;
import fr.groupbees.application.TeamLeagueOptions;
import fr.groupbees.domain.TeamStats;
import fr.groupbees.domain_transform.TeamStatsDatabaseIOConnector;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.joda.time.Instant;

import javax.inject.Inject;

public class TeamStatsBigQueryIOAdapter implements TeamStatsDatabaseIOConnector {

    private final TeamLeagueOptions options;

    @Inject
    public TeamStatsBigQueryIOAdapter(TeamLeagueOptions options) {
        this.options = options;
    }

    @Override
    public BigQueryIO.Write<TeamStats> write() {
        return BigQueryIO.<TeamStats>write()
                .withMethod(BigQueryIO.Write.Method.FILE_LOADS)
                .to(options.getOutputDataset() + "." + options.getOutputTable())
                .withFormatFunction(TeamStatsBigQueryIOAdapter::toTeamStatsTableRow)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND);
    }

    private static TableRow toTeamStatsTableRow(final TeamStats teamStats) {
        return new TableRow()
                .set("teamName", teamStats.getTeamName())
                .set("teamTotalScore", teamStats.getTeamScore())
                .set("ingestionDate", new Instant().toString());
    }
}
