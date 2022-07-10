package fr.groupbees.application;

import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Default.Enum;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface TeamLeagueOptions extends PipelineOptions {

    @Description("Path of the input Json file to read from")
    String getInputJsonFile();

    void setInputJsonFile(String value);

    @Description("Path of the input CSV file to read from")
    String getInputCsvFile();

    void setInputCsvFile(String value);

    @Description("File separator for CSV file")
    String getInputFileSeparator();

    void setInputFileSeparator(String value);

    @Description("Topic input subscription")
    String getInputSubscription();

    void setInputSubscription(String value);

    @Description("Path of the file to write to")
    String getTeamLeagueDataset();

    void setTeamLeagueDataset(String value);

    @Description("Team stats table")
    String getTeamStatsTable();

    void setTeamStatsTable(String value);

    @Description("BQ write method")
    @Default
    @Enum("FILE_LOADS")
    BigQueryIO.Write.Method getBqWriteMethod();

    void setBqWriteMethod(BigQueryIO.Write.Method value);
}
