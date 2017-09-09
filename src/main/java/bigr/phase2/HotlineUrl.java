package bigr.phase2;

/**
 * POJO for Hotline urls. Maybe would transform in global url
 */
public class HotlineUrl {
  private String url;
  private Integer count;
  private String category;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public static String getName(String url) {
    return url.replace("http://hotline.ua/", "").replace("/", "_");
  }
}
