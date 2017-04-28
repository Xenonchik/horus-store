import bigr.BiProductCsvDao;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Blahblahblah
 */
public class TestBigr {

  @Test
  public void testGetSku() {
    BiProductCsvDao ps = new BiProductCsvDao("");
    assertEquals("1111", ps.getSKU("(sdffs) dsfs (sdfsdfs) (1111)(ddffd"));
  }

}
