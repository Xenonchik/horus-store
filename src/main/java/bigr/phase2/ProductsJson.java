package bigr.phase2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

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
}
