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

    products.put(1, new Product(1, "Note 10", 25000000, "use pen", "Samsung", "1.jpeg"));

    products.put(2, new Product(2, "Iphone XS Max 256GB", 20000000, " not use pen", "Iphone", "2.jpeg"));

    products.put(3, new Product(3, "Iphone7", 15, "description", "supplier", "3.jpeg"));

    products.put(4, new Product(4, "Iphone7", 15, "description", "supplier", "5.jpeg"));

    products.put(5, new Product(5, "Iphone7", 15, "description", "supplier", "5.jpeg"));

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
