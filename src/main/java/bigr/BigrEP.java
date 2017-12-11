package bigr;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import domain.CatStore;
import domain.Product;
import domain.Store;
import parser.CategoryProcessor;
import stores.StoreManager;
import stores.antoshka.AntoshkaProcessor;
import stores.rozetka.RozetkaProcessor;

/**
 * Blahblahblah
 */
public class BigrEP {

  final static Logger log = LoggerFactory.getLogger(BigrEP.class);

  public static final String DB_URL = "jdbc:postgresql://localhost/eye?user=postgres&password=postgrespass&currentSchema=bigr";

  private static Dao<BiProduct, Integer> productDao = null;
  private static Dao<BiBrand, Integer> brandDao = null;
  private static Dao<Sku, Integer> skuDao = null;
  private static ConnectionSource connectionSource;

  static {
    try {
      connectionSource = new JdbcConnectionSource(DB_URL);


      productDao =
        DaoManager.createDao(connectionSource, BiProduct.class);
      skuDao =
          DaoManager.createDao(connectionSource, Sku.class);
      brandDao =
          DaoManager.createDao(connectionSource, BiBrand.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {

//    setBrands();

    TableUtils.clearTable(connectionSource, BiProduct.class);
    Long t1 = System.currentTimeMillis();
    rozetkaParse();
//    antoshkaParse();
    log.info("Total time = " + (System.currentTimeMillis() - t1));
//    setSku();

    connectionSource.close();
  }

  static void rozetkaParse() throws Exception {
    List<BiProduct> productsTotal = Lists.newArrayList();
    Set<String> brands = getBrands();
    Store store = new Store();
    StoreManager sm = new RozetkaProcessor();
    List<String> urls = rozCats;
    for (String url : urls) {
      CatStore testCat = new CatStore();
      testCat.setStore(store);
      testCat.setUrl(url);

      List<Product> products = new CategoryProcessor().process(testCat, sm);
      products.forEach(product -> {
            BiProduct biProduct = BiProduct.fromProduct(product);
            biProduct.setStore("ROZETKA");
            biProduct.setBrand(brands);
            productsTotal.add(biProduct);
          }
      );

    }
    saveProducts(productsTotal);

  }

  public static void saveProducts(List<BiProduct> productsTotal) {
    productsTotal.forEach(product -> {
          try {
            productDao.create(product);
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
    );
  }

  public static void setSku() throws SQLException {
    List<BiProduct> products = productDao.queryForAll();
    List<Sku> skus = skuDao.queryForAll();

    for(BiProduct product : products) {
      boolean hasSku = false;
      for(Sku sku : skus) {
        if(product.getStore().equals(sku.getStore())
            && product.getName().equals(sku.getName())
            ) {
          hasSku = true;
        }
      }
      if(!hasSku) {
        log.info("No SKU for product: " + product.getName() + "[" + product.getStore() + "]");
        switch (product.getStore()) {
          case "ROZETKA": {
            Sku sku = new RozetkaSkuBuilder().buildSku(product);
            skuDao.create(sku);
            break;
          }
          case "ANTOSHKA": {
            Sku sku = new AntoshkaSkuBuilder().buildSku(product);
            skuDao.create(sku);
            try {
              Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
          }
        }
      }

    }
  }

  static void antoshkaParse() throws Exception {
    List<BiProduct> productsTotal = Lists.newArrayList();
    Set<String> brands = getBrands();
    Store store = new Store();
    StoreManager sm = new AntoshkaProcessor();
    List<String> urls = antCats;
    for (String url : urls) {
      CatStore testCat = new CatStore();
      testCat.setStore(store);
      testCat.setUrl(url);

      List<Product> products = new CategoryProcessor().process(testCat, sm);
      products.forEach(product -> {
            BiProduct biProduct = BiProduct.fromProduct(product);
            biProduct.setStore("ANTOSHKA");
            biProduct.setBrand(brands);
            productsTotal.add(biProduct);
          }
      );

    }
    saveProducts(productsTotal);
  }

  public static void setBrands() {
    Set<String> result = Sets.newHashSet();

    RozetkaBrands rb = new RozetkaBrands();
    for (String url : rozCats) {
      result.addAll(rb.getBrands(url));
    }

    AntoshkaBrands ab = new AntoshkaBrands();
    for (String url : antCats) {
      result.addAll(ab.getBrands(url));
    }

    result.forEach(brand -> {
          BiBrand bibrand = new BiBrand(brand);
          try {
            brandDao.create(bibrand);
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
    );
  }

  public static Set<String> getBrands() {
    Set<String> result = Sets.newHashSet();
    try {
      brandDao.queryForAll().forEach(brand -> {
        result.add(brand.getBrand());
      });
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static List<String> rozCats = Lists.asList(
      "https://rozetka.com.ua/s_centers/c103303/22852=26749/",
      new String[]{
          "https://rozetka.com.ua/rattles/c103323/22865=9672/",
          "https://rozetka.com.ua/rattles/c103323/22865=9673/",
          "https://rozetka.com.ua/igrushki-dlya-malyshey/c100193/22666=9198/",
          "https://rozetka.com.ua/interactive_toys/c98159/",
          "https://rozetka.com.ua/stroller_toys/c1183125/",
          "https://rozetka.com.ua/igrushki-dlya-plyaga-pesochnitsi-i-vannoy/c4631200/naznachenie125685=dlya-vodi/",
          "https://rozetka.com.ua/igrushki-dlya-plyaga-pesochnitsi-i-vannoy/c4631200/naznachenie125685=dlya-peska/",
          "https://rozetka.com.ua/s_centers/c103303/22852=9649/",
          "https://rozetka.com.ua/s_centers/c103303/22852=381663/",
          "https://rozetka.com.ua/s_centers/c103303/22852=9650/",
          "https://rozetka.com.ua/s_centers/c103303/22852=26749/",
          "https://rozetka.com.ua/igrushki-dlya-malyshey/c100193/22666=196362/",
          "https://rozetka.com.ua/chudomobili-hodunki-kachalki/c104034/22924=9777/",
          "https://rozetka.com.ua/chudomobili-hodunki-kachalki/c104034/22924=9775/",
          "https://rozetka.com.ua/chudomobili-hodunki-kachalki/c104034/22924=9776/",
          "https://rozetka.com.ua/detskie-kolyaski/c100389/",
          "https://rozetka.com.ua/detskie-avtokresla/c83687/",
          "https://rozetka.com.ua/aksessuary-dlya-kolyasok-i-avtokresel/c101746/",
          "https://rozetka.com.ua/slingi-ryukzaki-perenoski/c212775/",
          "https://rozetka.com.ua/detskie-velosipedy/c104132/",
          "https://rozetka.com.ua/detskie-elektro-i-velomobili/c91143/",
          "https://rozetka.com.ua/k_skates/c189145/",
          "https://rozetka.com.ua/k_scooter/c139406/",
          "https://rozetka.com.ua/detskie-velosipedy/c104132/22931=29088/",
          "https://rozetka.com.ua/k_roller_skates/c189193/",
          "https://rozetka.com.ua/playgrounds/c139442/",
          "https://rozetka.com.ua/detskie-krovatki/c101909/",
          "https://rozetka.com.ua/manege/c100076/",
          "https://rozetka.com.ua/komody-i-pelenatory/c103214/",
          "https://rozetka.com.ua/party-i-molberty/c100194/",
          "https://rozetka.com.ua/stulchiki-dlya-kormleniya/c100077/",
          "https://rozetka.com.ua/aksessuary-dlya-detskoy-mebeli/c108706/23132=268119/",
          "https://rozetka.com.ua/mobile/c102300/",
          "https://rozetka.com.ua/babymonitors/c146154/",
          "https://rozetka.com.ua/detskie-uvlajniteli-vozduha/c270133/",
          "https://bt.rozetka.com.ua/nastolnye-lampy/c105280/filter/106954=detskie/",
          "https://rozetka.com.ua/nochniki/c4628793/",
          "https://rozetka.com.ua/igrushki-nochniki/c159570/",
          "https://rozetka.com.ua/nastoljnye-igry-i-golovolomki/c98280/",
          "https://rozetka.com.ua/literatura/c4005167/vozrast-rebenka-68527=372538,372550,372562/",
          "https://rozetka.com.ua/detskie-kompyutery/c103520/",
          "https://rozetka.com.ua/skrapbuking-i-kardmejking/c4632201/",
          "https://rozetka.com.ua/applikacii-i-podelki-iz-bumagi/c4632159/22826=76209,poloski-dlya-kvillinga/",
          "https://rozetka.com.ua/mozaiki4632138/c4632138/",
          "https://rozetka.com.ua/detskie-muzykalnye-instrumenty/c102876/",
          "https://rozetka.com.ua/lepka4632166/c4632166/",
          "https://rozetka.com.ua/nabory-dlya-nauchnyh-issledovaniy/c102843/",
          "https://rozetka.com.ua/razlichnye-nabory-dlya-detskogo-tvorchestva/c4632145/",
          "https://rozetka.com.ua/puzzles/c102848/",
          "https://rozetka.com.ua/risovanie4632117/c4632117/",
          "https://rozetka.com.ua/naklejki-stikery-tatu/c4632124/",
          "https://rozetka.com.ua/izgotovlenie-ukrashenij/c4632131/",
          "https://rozetka.com.ua/tvorchestvo-v-3d/c4632152/",
          "https://rozetka.com.ua/applikacii-i-podelki-iz-bumagi/c4632159/",
          "https://rozetka.com.ua/vyshivanie/c4632173/",
          "https://rozetka.com.ua/izgotovlenie-igrushek4632180/c4632180/",
          "https://rozetka.com.ua/pletenie4632187/c4632187/",
          "https://rozetka.com.ua/shitje-i-vyazanie/c4632194/",
          "https://rozetka.com.ua/dekupazh-i-rospisj/c4632208/https://rozetka.com.ua/robototehnika/c4630482/",
          "https://rozetka.com.ua/rc_toys/c97422/",
          "https://rozetka.com.ua/building_kits/c97420/22581=8856,8857/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=286251/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222030/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222079/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222044/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222170/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222177/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222058/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222051/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=485262/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=236045/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222205/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222212/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222163/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222156/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222149/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222100/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222114/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222093/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=223887/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222107/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=230328/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222135/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222142/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222037/https://rozetka.com.ua/avtomobilnye-treki/c102308/",
          "https://rozetka.com.ua/igrushechnoe-orujie/c104013/22923=9769/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222177/",
          "https://rozetka.com.ua/railroad/c99364/",
          "https://rozetka.com.ua/igrushechnoe-orujie/c104013/",
          "https://rozetka.com.ua/kollektsionnye-modelki/c103338/",
          "https://rozetka.com.ua/igrushechnye-mashinki-i-tehnika/c102003/",
          "https://rozetka.com.ua/modelirovanie/c104335/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222156/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222100/",
          "https://rozetka.com.ua/rc_toys/c97422/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/",
          "https://rozetka.com.ua/gaming_kits/c99674/filter/64649=222037/"
      }
  );

  public static List<String> antCats = Lists.asList(
  "http://antoshka.ua/igrushki/lego.html",
      new String[]{
  "http://antoshka.ua/igrushki/aktivnyy-otdyh.html",
  "http://antoshka.ua/igrushki/dlya-malenkih-detey.html",
  "http://antoshka.ua/igrushki/mashinki-i-tehnika.html",
  "http://antoshka.ua/igrushki/myagkie-igrushki.html",
  "http://antoshka.ua/igrushki/konstruktor.html",
  "http://antoshka.ua/igrushki/kukly-i-aksessuary.html",
  "http://antoshka.ua/igrushki/nastolnye-igry.html",
  "http://antoshka.ua/igrushki/tvorchestvo.html",
  "http://antoshka.ua/igrushki/roboty-i-figurki.html",
  "http://antoshka.ua/igrushki/igrushechnoe-oruzhie.html",
  "http://antoshka.ua/igrushki/pazzly.html",
  "http://antoshka.ua/igrushki/tematicheskie-igrovye-nabory.html",
  "http://antoshka.ua/igrushki/muzykalnye-instrumenty.html",
  "http://antoshka.ua/igrushki/knigi.html",
  "http://antoshka.ua/igrushki/dekorativnaya-detskaya-kosmetika-i-aksessuary.html",
  "http://antoshka.ua/igrushki/vse-dlya-prazdnika.html",
  "http://antoshka.ua/igrushki/elementy-pitaniya-i-fonari.html",
  "http://antoshka.ua/igrushki/kancelyarskie-tovary.html",
  "http://antoshka.ua/igrushki/krupnogabaritnye-igrushki.html"
  });
}
