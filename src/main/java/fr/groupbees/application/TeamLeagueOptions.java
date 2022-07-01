package fr.groupbees.application;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface TeamLeagueOptions extends PipelineOptions {

    @Description("Path of the file to read from")
    @Default.String("gs://apache-beam-samples/shakespeare/kinglear.txt")
    String getInputFile();

    void setInputFile(String value);

    @Description("Path of the file to read from")
    @Default.Character(',')
    char getInputFileSeparator();

    void setInputFileSeparator(char value);

    @Description("Path of the file to read from")
    @Default.String("gs://apache-beam-samples/shakespeare/kinglear.txt")
    String getInputSubscription();

    void setInputSubscription(String value);

    @Description("Path of the file to write to")
    String getOutputDataset();

    void setOutputDataset(String value);

    @Description("Path of the file to write to")
        // @Validation.Required
    String getOutputTable();

    void setOutputTable(String value);
}
