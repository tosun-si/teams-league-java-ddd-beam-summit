package fr.groupbees.infrastructure.io;

import lombok.Builder;
import lombok.Getter;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;

import java.io.Serializable;

@Builder
@Getter
public class PipelineConf implements Serializable {

    private final String inputJsonFile;
    private final String inputCsvFile;
    private final char inputFileSeparator;
    private final String inputSubscription;
    private final String teamLeagueDataset;
    private final String teamStatsTable;
    private final BigQueryIO.Write.Method bqWriteMethod;
}
