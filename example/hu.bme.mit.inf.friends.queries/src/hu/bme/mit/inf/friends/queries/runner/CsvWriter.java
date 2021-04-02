/***********************************************************************************************************************
 * Source: https://mkyong.com/java/how-to-export-data-to-csv-file-java/ 
 *         https://github.com/mkyong/core-java/blob/master/java-io/src/main/java/com/mkyong/io/csv/CsvWriterSimple.java
 ***********************************************************************************************************************/

package hu.bme.mit.inf.friends.queries.runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvWriter {

    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";
    private static final String DEFAULT_SEPARATOR = SEMICOLON;
    private static final String DOUBLE_QUOTES = "\"";
    private static final String EMBEDDED_DOUBLE_QUOTES = "\"\"";
    private static final String NEW_LINE_UNIX = "\n";
    private static final String NEW_LINE_WINDOWS = "\r\n";

    public String convertToCsvFormat(final String[] line) {
        return convertToCsvFormat(line, DEFAULT_SEPARATOR);
    }

    public String convertToCsvFormat(final String[] line, final String separator) {
        return convertToCsvFormat(line, separator, true);
    }

    // if quote = true, all fields are enclosed in double quotes
    public String convertToCsvFormat(
            final String[] line,
            final String separator,
            final boolean quote) {

        return Stream.of(line)                              // convert String[] to stream
                .map(l -> formatCsvField(l, quote))         // format CSV field
                .collect(Collectors.joining(separator));    // join with a separator

    }

    // put your extra login here
    private String formatCsvField(final String field, final boolean quote) {

        String result = field;

        if (result.contains(SEMICOLON)
                || result.contains(DOUBLE_QUOTES)
                || result.contains(NEW_LINE_UNIX)
                || result.contains(NEW_LINE_WINDOWS)) {

            // if field contains double quotes, replace it with two double quotes \"\"
            result = result.replace(DOUBLE_QUOTES, EMBEDDED_DOUBLE_QUOTES);

            // must wrap by or enclosed with double quotes
            result = DOUBLE_QUOTES + result + DOUBLE_QUOTES;

        } else {
            // should all fields enclosed in double quotes
            if (quote) {
                result = DOUBLE_QUOTES + result + DOUBLE_QUOTES;
            }
        }

        return result;

    }

    // a standard FileWriter, CSV is a normal text file
    void writeToCsvFile(List<String[]> list, File file, boolean append) throws IOException {

        List<String> collect = list.stream()
                .map(this::convertToCsvFormat)
                .collect(Collectors.toList());

        // CSV is a normal text file, need a writer
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            for (String line : collect) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    void writeToCsvFile(List<String[]> list, File file) throws IOException {
    	writeToCsvFile(list, file, false);
    }

    // a standard FileWriter, CSV is a normal text file
    void writeToCsvFile(String[] array, File file, boolean append) throws IOException {

    	String line = convertToCsvFormat(array);
//        // CSV is a normal text file, need a writer
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            bw.write(line);
            bw.newLine();
        }
    }

    void writeToCsvFile(String[] array, File file) throws IOException {
    	writeToCsvFile(array, file, false);
    }
}
