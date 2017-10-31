package bigr.phase2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import domain.Product;

/**
 * Blahblahblah
 */
public class ProductsJson {

  private Gson gson = new GsonBuilder().setPrettyPrinting().create();

  public void write(List<Product> products, String path) throws IOException {
    Writer writer = new FileWriter(path);
    gson.toJson(products, writer);
    writer.close();
  }

  public List<Product> read(String path) throws FileNotFoundException {
    Reader reader = new FileReader(path);
    return Lists.newArrayList((Product[]) gson.fromJson(reader, Product[].class));
  }
}
