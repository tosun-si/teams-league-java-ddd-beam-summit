package fr.groupbees.infrastructure.io.csvfile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.beam.sdk.io.FileIO.ReadableFile;
import org.apache.beam.sdk.transforms.DoFn;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.Channels;

public class CsvParser<T> extends DoFn<ReadableFile, T> {

    private final Class<T> currentClass;
    private final char separator;

    public static <T> CsvParser<T> from(Class<T> currentClass, char separator) {
        return new CsvParser<>(currentClass, separator);
    }

    private CsvParser(Class<T> currentClass, char separator) {
        this.currentClass = currentClass;
        this.separator = separator;
    }

    public void processElement(@Element ReadableFile element, OutputReceiver<T> receiver) {
        InputStream inputStream = null;
        try {
            inputStream = Channels.newInputStream(element.open());
        } catch (IOException e) {
            throw new IllegalStateException("The element corresponding to a CSV line can't be parsed");
        }

        final Reader reader = new InputStreamReader(inputStream);

        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder()
                .withSeparator(separator)
                .build();

        CSVReader csvReaderBuilder = new CSVReaderBuilder(reader)
                .withCSVParser(rfc4180Parser)
                .build();

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReaderBuilder)
                .withType(currentClass)
                .withSeparator(separator)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        csvToBean.iterator().forEachRemaining(receiver::output);
    }
}
