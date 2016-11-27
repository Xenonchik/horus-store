package beholder.util;

import org.apache.commons.csv.CSVFormat;

/**
 * Created by serge on 6/9/16.
 */
public class CSVUtils {
  final public static CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';').withQuote(null);
}
