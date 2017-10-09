package bigr.phase2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import domain.Product;

/**
 * Blahblahblah
 */
public class ProductsJson {

  private Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private static final Type PRODUCTS_TYPE = new TypeToken<List<Product>>() {}.getType();

  public void write(List<Product> products, String path) throws IOException {
    Writer writer = new FileWriter(path);
    gson.toJson(products, writer);
    writer.close();
  }

  public List<Product> read(String path) throws FileNotFoundException {
    JsonReader reader =new JsonReader( new FileReader(path));
    List<Product> products = gson.fromJson(reader, PRODUCTS_TYPE);
    return products;
  }
}
