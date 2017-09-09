package bigr.phase2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * Blahblahblah
 */
public class HotlineUrlsJson {
  private String path = "/opt/data/bigr/hotline-urls.json";

  public HotlineUrlsJson() {}

  public HotlineUrlsJson(String path) {
    this.path = path;
  }

  public List<HotlineUrl> readUrls() throws FileNotFoundException {
    Gson gson = new Gson();
    JsonReader reader = new JsonReader(new FileReader(path));
    List<HotlineUrl> data = Lists.newArrayList((HotlineUrl[])gson.fromJson(reader, HotlineUrl[].class));
    return data;
  }
}
