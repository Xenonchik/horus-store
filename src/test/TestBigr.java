import bigr.ProductSaver;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Blahblahblah
 */
public class TestBigr {

  @Test
  public void testGetSku() {
    ProductSaver ps = new ProductSaver("");
    assertEquals("1111", ps.getSKU("(sdffs) dsfs (sdfsdfs) (1111)(ddffd"));
  }

}
