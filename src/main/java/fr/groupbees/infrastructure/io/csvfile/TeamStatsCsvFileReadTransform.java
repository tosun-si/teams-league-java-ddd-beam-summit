package fr.groupbees.infrastructure.io.csvfile;

import fr.groupbees.domain.TeamStatsRaw;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

import static org.apache.beam.sdk.values.TypeDescriptor.of;

public class TeamStatsCsvFileReadTransform<T extends Serializable> extends PTransform<PBegin, PCollection<TeamStatsRaw>> {

    private final String filePath;
    private final char separator;

    public TeamStatsCsvFileReadTransform(String filePath, char separator) {
        this.filePath = filePath;
        this.separator = separator;
    }

    @Override
    public PCollection<TeamStatsRaw> expand(PBegin input) {
        return input.apply(FileIO.match().filepattern(filePath))
                .apply(FileIO.readMatches())
                .apply("Parse CSV file", ParDo.of(CsvParser.from(TeamStatsCsv.class, separator)))
                .apply("Map to Team stats Raw", MapElements.into(of(TeamStatsRaw.class)).via(this::toTeamStatsRaw))
                .setCoder(SerializableCoder.of(TeamStatsRaw.class));
    }

    private TeamStatsRaw toTeamStatsRaw(final TeamStatsCsv teamStatsCsv) {
        return null;
    }
}
