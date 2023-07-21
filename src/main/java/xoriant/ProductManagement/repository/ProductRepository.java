package xoriant.ProductManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import xoriant.ProductManagement.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Optional<Product> findById(int id);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %:key% OR p.description LIKE %:key% OR p.quantity LIKE %:key% OR p.vendor LIKE %:key% OR p.price LIKE %:key% OR p.category LIKE %:key% OR p.status LIKE %:key% OR p.status LIKE %:key% OR p.created_at LIKE %:key% OR p.updated_at LIKE %:key%")
	//@Query("SELECT p FROM Product p WHERE :key IN (p.name, p.description,p.quantity,p.vendor,p.price,p.category,p.status,p.created_at,p.updated_at)")
	List<Product> search(String key);


	
}
