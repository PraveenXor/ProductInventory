package xoriant.ProductManagement.services;
import java.util.List;
import java.util.Optional;

import xoriant.ProductManagement.model.Product;

public interface ProductService {
	public Product createProduct(Product prod) ;
	///public List<Product> getAllProduct();
	public List<Product> getAllProducts(String columnName,String sortBy,Integer pageNo, Integer pageSize);
	public Optional<Product> getProductById(int id);
	public boolean updateProduct(int id, Product updatedProduct);
	public boolean deleteProduct(int id);
	public List<Product> searchProducts(String key);
}
