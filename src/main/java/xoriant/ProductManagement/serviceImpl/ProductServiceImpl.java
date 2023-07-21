package xoriant.ProductManagement.serviceImpl;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xoriant.ProductManagement.model.Product;
import xoriant.ProductManagement.repository.ProductRepository;
import xoriant.ProductManagement.services.ProductService;

@Service

public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository prodRepository;

	public ProductServiceImpl(ProductRepository prodRepository) {
	this.prodRepository = prodRepository;
	}

	@Override
	public Product createProduct(Product prod) {
		
		prodRepository.save(prod);
		return prod;
	}

	public List<Product> getAllProducts(String columnName, String sortBy, Integer pageNo, Integer pageSize) {
		Pageable paging = null;
		if (sortBy.contains("asc")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(columnName).ascending());

		} else {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(columnName).descending());

		}
		Page<Product> pagedResult = prodRepository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Product>();
		}
	}

	@Override
	public Optional<Product> getProductById(int id) {

		return prodRepository.findById(id);
	}

	@Override
	public boolean updateProduct(int id, Product updatedProduct) {
		Optional<Product> optionalProduct = prodRepository.findById(id);
	     
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setDescription(updatedProduct.getDescription());
			product.setQuantity(updatedProduct.getQuantity());
			product.setPrice(updatedProduct.getPrice());
			product.setCategory(updatedProduct.getCategory());
			product.setStatus(updatedProduct.getStatus());
			product.setVendor(updatedProduct.getVendor());
			product.setUpdated_at(LocalDateTime.now());
			prodRepository.save(product);
			return true;
		}
		else {
			 if(!prodRepository.findById(id).isPresent())
			 {
				 return false;
			
			 }
		}
		return false;
		
	}

	@Override
	public boolean deleteProduct(int id) {
		Optional<Product> optionalProduct = prodRepository.findById(id);

		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			prodRepository.delete(product);
			return true;
		}
		return false;
	}

	@Override
	public List<Product> searchProducts(String key) {

		return prodRepository.search(key);
	}

}
