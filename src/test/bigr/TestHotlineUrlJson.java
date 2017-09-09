package bigr;

import static junit.framework.TestCase.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import bigr.phase2.HotlineUrl;
import bigr.phase2.HotlineUrlsJson;

/**
 * Blahblahblah
 */
public class TestHotlineUrlJson {

  @Test
  public void testRead() throws FileNotFoundException {
    HotlineUrlsJson reader = new HotlineUrlsJson();
    List<HotlineUrl> urls = reader.readUrls();
    assertEquals(54, urls.size());
  }

  @Test
  public void testUrlNames() {
    assertEquals("deti_transformery_", HotlineUrl.getName("http://hotline.ua/deti/transformery/"));
    assertEquals("deti_igrushki-dlya-malchikov_27699_", HotlineUrl.getName("http://hotline.ua/deti/igrushki-dlya-malchikov/27699/"));
  }
}
