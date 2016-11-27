package beholder.persistence.csv;

import static beholder.util.CSVUtils.CSV_FORMAT;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * Blahblahblah
 */
public class CsvDataProvider {

  private final List<String> fileHeader;
  private final String filename;
  private CSVPrinter csvFilePrinter;
  private FileWriter fileWriter;
  private Reader fileReader;

  public CsvDataProvider(String filename, List<String> fileHeader) {
    this.fileHeader = fileHeader;
    this.filename = filename;
  }

  public void printRecord(Collection record) throws IOException {
    initWriter();
    csvFilePrinter.printRecord(record);
  }

  private void initWriter() throws IOException {
    if(fileWriter != null) return;
    fileWriter = new FileWriter(filename);
    csvFilePrinter = new CSVPrinter(fileWriter, CSV_FORMAT);
    csvFilePrinter.printRecord(fileHeader);
  }

  public Iterable<CSVRecord> getAllRecords() throws IOException {
    fileReader = new FileReader(filename);
    return CSV_FORMAT.parse(fileReader);
  }

  public List<String> getFileHeader() {
    return fileHeader;
  }

  public String getFilename() {
    return filename;
  }
}
