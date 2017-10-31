package bigr.phase2.cat;

import parser.source.HtmlSourceBuilder;

/**
 * Blahblahblah
 */
public class HotlineCategoryService {

  private HtmlSourceBuilder htmlSourceBuilder;
  private CategoryParser categoryParser;

  public HotlineCategoryService() {
    htmlSourceBuilder = new HtmlSourceBuilder();
    categoryParser = new CategoryParser();
  }

  public CategoryInfo getCategoryInfo(String url) {
    String html = htmlSourceBuilder.getSource(url).getContent();
    return categoryParser.parse(html);
  }

}
