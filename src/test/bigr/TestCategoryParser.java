package bigr;

import static junit.framework.TestCase.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import bigr.phase2.HotlineUrl;
import bigr.phase2.HotlineUrlsJson;
import bigr.phase2.cat.CategoryInfo;
import bigr.phase2.cat.HotlineCategoryService;
import junit.framework.Assert;

public class TestCategoryParser {

  private HotlineCategoryService service = new HotlineCategoryService();

  @Test
  public void testCount() {
    String catUrl = "http://hotline.ua/deti/razvivayuschie-igrushki/";
    CategoryInfo info = service.getCategoryInfo(catUrl);

    assertEquals(3405, info.getProductsCount().intValue());
    assertEquals("Игрушки для самых маленьких", info.getName());
  }

  @Test
  public void testCountWithTwo() {
    String catUrl = "http://hotline.ua/dacha_sad/ograzhdeniepoint-zaborpoint-palisadniki/377196/";
    CategoryInfo info = service.getCategoryInfo(catUrl);

    assertEquals(3, info.getProductsCount().intValue());
    assertEquals("Ограждение для игровых площадок", info.getName());
  }
}
