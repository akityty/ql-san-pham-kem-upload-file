package service;

import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements ProductService {
  private static Map<Integer, Product> products;

  static {
    products = new HashMap<>();

    products.put(1, new Product(1, "Note 10", 25000000, "use pen", "Samsung", "https://cdn.fptshop.com.vn/Uploads/Originals/2019/8/8/637008711602926121_SS-note-10-pl-den-1-1.png"));

    products.put(2, new Product(2, "Iphone XS Max 256GB", 20000000, " not use pen", "Iphone", "https://images.fpt.shop/unsafe/fit-in/200x200/filters:quality(90):fill(white)/cdn.fptshop.com.vn/Uploads/Originals/2019/8/1/637002351614380313_636749593270587915_iphoneXS-1o.png"));

    products.put(3, new Product(3, "Iphone7", 15, "description", "supplier", "https://cdn.tgdd.vn/Products/Images/7498/200439/midea-ac100-18b-300x300.jpg"));

    products.put(4, new Product(4, "Iphone7", 15, "description", "supplier", "https://cdn.tgdd.vn/Products/Images/1922/92601/toshiba-rc-18nmfvn-wt-daidien-300x300.jpg"));

    products.put(5, new Product(5, "Iphone7", 15, "description", "supplier", "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6196/6196727_sd.jpg"));

  }


  @Override
  public List<Product> findAll() {
    return new ArrayList<>(products.values());
  }

  @Override
  public Product findById(int id) {
    return products.get(id);
  }

  @Override
  public Product findByName(String name) {
    List<Product> products = findAll();
    for (Product product : products) {
      if (name.equals(product.getName())) {
        return product;
      }
    }
    return null;
  }

  @Override
  public void save(Product product) {
    products.put(product.getId(), product);
  }

  @Override
  public void update(int id, Product product) {
    products.put(id, product);
  }

  @Override
  public void delete(int id) {
    products.remove(id);
  }
}
