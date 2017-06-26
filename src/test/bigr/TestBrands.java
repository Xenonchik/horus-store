package bigr;

import java.util.Set;

import org.junit.Test;

/**
 * Created by serge on 4/26/17.
 */
public class TestBrands {

  @Test
  public void testGetRozBrands() {
    RozetkaBrands rb = new RozetkaBrands();
    Set<String> brands = rb.getBrands("http://rozetka.com.ua/nastoljnye-igry-i-golovolomki/c98280/");

    brands.clear();
  }

  @Test
  public void testGetAntBrands() {
    AntoshkaBrands rb = new AntoshkaBrands();
    Set<String> brands = rb.getBrands("http://antoshka.ua/igrushki/aktivnyy-otdyh.html");

    brands.clear();
  }


}
