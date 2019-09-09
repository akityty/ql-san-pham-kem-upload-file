package controller;

import model.Product;
import service.ProductService;
import service.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ProductServlet", urlPatterns = "/products")
//---------------------------------------------------------------
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProductServlet extends javax.servlet.http.HttpServlet {
  //-----------------------------------------------------------------------------
  private static final long serialVersionUID = 1L;

  public static final String SAVE_DIRECTORY = "pictures";

  public ProductServlet() {
    super();
  }


  private ProductService productService = new ProductServiceImpl();

  protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    String action = request.getParameter("action");
    if (action == null) {
      action = "";
    }
    switch (action) {
      case "create":
        createProduct(request, response);
        break;
      case "edit":
        editProduct(request, response);
        break;
      case "delete":
        deleteProduct(request,response);
        break;
      default:
        viewProduct(request,response);
        break;
    }
  }

  private void viewProduct(HttpServletRequest request, HttpServletResponse response) {
    String name = request.getParameter("searchByName");
    Product product = productService.findByName(name);
    RequestDispatcher dispatcher;
    if(product == null){
      dispatcher = request.getRequestDispatcher("error-404.jsp");
    }else{
      request.setAttribute("product",product);
      dispatcher =request.getRequestDispatcher("product/view.jsp");
    }
    try {
      dispatcher.forward(request,response);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }

  private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
    int id = Integer.parseInt(request.getParameter("id"));
    Product product = productService.findById(id);
    productService.delete(product.getId());
    request.setAttribute("message","product was deleted");
    request.setAttribute("product",product);
    RequestDispatcher dispatcher = request.getRequestDispatcher("product/delete.jsp");
    try{
      dispatcher.forward(request,response);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }

  private void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    RequestDispatcher dispatcher;
    try {
      int id = Integer.parseInt(request.getParameter("id"));
      String name = request.getParameter("name");
      int price = Integer.parseInt(request.getParameter("price"));
      String description = request.getParameter("description");
      String supplier = request.getParameter("supplier");
      String picture = "";

      String appPath = request.getServletContext().getRealPath("");
      appPath = appPath.replace('\\', '/');
      String fullSavePath = null;
      if (appPath.endsWith("/")) {
        fullSavePath = appPath + SAVE_DIRECTORY;
      } else {
        fullSavePath = appPath + "/" + SAVE_DIRECTORY;
      }
      File fileSaveDir = new File(fullSavePath);
      if (!fileSaveDir.exists()) {
        fileSaveDir.mkdir();
      }
      for (Part part : request.getParts()) {
        String fileName = extractFileName(part);
        if (fileName != null && fileName.length() > 0) {
          String filePath = fullSavePath + File.separator + fileName;
          part.write(filePath);
          picture = fileName;
        }
      }


      Product product = this.productService.findById(id);
      product.setId(id);
      product.setName(name);
      product.setPrice(price);
      product.setDescription(description);
      product.setSupplier(supplier);
      if (picture!="") product.setPicture(picture);
      this.productService.update(id, product);
      request.setAttribute("product",product);
      request.setAttribute("message", "Product information was updated");
      dispatcher = request.getRequestDispatcher("product/edit.jsp");
    } catch (NumberFormatException | IOException e) {
      dispatcher = request.getRequestDispatcher("error-404.jsp");
    }
    try {
      dispatcher.forward(request, response);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }

  private void createProduct(HttpServletRequest request, HttpServletResponse response) {
    String name = request.getParameter("name");
    int price;
    String description;
    String supplier;
    String picture = "";
    RequestDispatcher dispatcher;

    try {
      price = Integer.parseInt(request.getParameter("price"));
      description = request.getParameter("description");
      supplier = request.getParameter("supplier");
      String appPath = request.getServletContext().getRealPath("");
      appPath = appPath.replace('\\', '/');
      String fullSavePath = null;
      if (appPath.endsWith("/")) {
        fullSavePath = appPath + SAVE_DIRECTORY;
      } else {
        fullSavePath = appPath + "/" + SAVE_DIRECTORY;
      }
      File fileSaveDir = new File(fullSavePath);
      if (!fileSaveDir.exists()) {
        fileSaveDir.mkdir();
      }
      for (Part part : request.getParts()) {
        String fileName = extractFileName(part);
        if (fileName != null && fileName.length() > 0) {
          String filePath = fullSavePath + File.separator + fileName;
          System.out.println("Write attachment to file: " + filePath);

          part.write(filePath);
          picture = fileName;
        }
      }
      int id = (int) (Math.random() * 100000);

      Product product = new Product(id, name, price, description, supplier, picture);
      this.productService.save(product);
      dispatcher = request.getRequestDispatcher("product/create.jsp");
      request.setAttribute("message", "New product was created");
    } catch (NumberFormatException | IOException | ServletException e) {
      dispatcher = request.getRequestDispatcher("error-404.jsp");
    }
    try {
      dispatcher.forward(request, response);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }

  private String extractFileName(Part part) {

    String contentDisp = part.getHeader("content-disposition");
    String[] items = contentDisp.split(";");
    for (String s : items) {
      if (s.trim().startsWith("filename")) {

        String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
        clientFileName = clientFileName.replace("\\", "/");
        int i = clientFileName.lastIndexOf('/');

        return clientFileName.substring(i + 1);
      }
    }
    return null;
  }

  protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    String action = request.getParameter("action");
    if (action == null) {
      action = "";
    }
    switch (action) {
      case "create":
        showCreateForm(request, response);
        break;
      case "edit":
        showEditForm(request, response);
        break;
      case "delete":
         showDeleteForm(request,response);
        break;
      case "view":
        break;
      default:
        showListProducts(request, response);
        break;
    }
  }

  private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
    int id = Integer.parseInt(request.getParameter("id"));
    Product product = productService.findById(id);
    RequestDispatcher dispatcher;
    if (product == null) {
      dispatcher = request.getRequestDispatcher("error-404.jsp");
    } else {
      request.setAttribute("product", product);
      dispatcher = request.getRequestDispatcher("product/edit.jsp");
    }
    try {
      dispatcher.forward(request, response);
    } catch (ServletException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void showCreateForm(HttpServletRequest request, HttpServletResponse response) {
    RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
    try {
      dispatcher.forward(request, response);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }

  private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) {
    int id = Integer.parseInt(request.getParameter("id"));
    Product product = productService.findById(id);
    RequestDispatcher dispatcher;
    if(product == null){
      dispatcher = request.getRequestDispatcher("error-404.jsp");
    }else{
      request.setAttribute("product",product);
      dispatcher =request.getRequestDispatcher("product/delete.jsp");
    }
    try {
      dispatcher.forward(request,response);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }

  private void showListProducts(HttpServletRequest request, HttpServletResponse response) {
    List<Product> products = productService.findAll();
    request.setAttribute("products", products);
    RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
    try {
      dispatcher.forward(request, response);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }

 /* private String extractFileName(Part part) {
    // form-data; name="file"; filename="C:\file1.zip"
    // form-data; name="file"; filename="C:\Note\file2.zip"
    String contentDisp = part.getHeader("content-disposition");
    String[] items = contentDisp.split(";");
    for (String s : items) {
      if (s.trim().startsWith("filename")) {
        // C:\file1.zip
        // C:\Note\file2.zip
        String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
        clientFileName = clientFileName.replace("\\", "/");
        int i = clientFileName.lastIndexOf('/');
        // file1.zip
        // file2.zip
        return clientFileName.substring(i + 1);
      }
    }
    return null;
  }*/
  }
