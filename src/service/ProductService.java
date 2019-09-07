package service;

import model.Product;

import java.util.List;

public interface ProductService {
  List<Product> findAll();
  Product findById(int id);
  Product findByName(String name);
  void save(Product product);
  void update(int id, Product product);
  void delete(int id);
}
