package xoriant.ProductManagement.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xoriant.ProductManagement.response.*;
import xoriant.ProductManagement.services.ProductService;
import xoriant.ProductManagement.exceptions.NullOrEmptyExceptionClass;
import xoriant.ProductManagement.exceptions.ProductNotfoundException;
import xoriant.ProductManagement.model.Product;

@RestController
@RequestMapping("/ims")
public class ProductManagementController {
	@Autowired
	ProductService product;
	
	@PostMapping("/products")
	public ResponseEntity<Product>  createProduct(@RequestBody Product product ) throws NullOrEmptyExceptionClass{
		if(product.getName()==null) {
			throw new NullOrEmptyExceptionClass();
		}
		return new ResponseEntity<>( this.product.createProduct(product),HttpStatus.CREATED);
	}

	// Get Product by Id
	@GetMapping("/products/{id}")
	public ResponseEntity<Optional<Product>> getProdById(@PathVariable(value = "id") Integer productId) {
		Optional<Product> productData = this.product.getProductById(productId);
		return ResponseEntity.ok().body(productData);
	}

	//Get All Product with pagination and sort
	@GetMapping(path = "/products")
	public ResponseEntity<List<Product>> getAllProduct(
			@RequestParam(defaultValue = "name") String columnName,
			@RequestParam(defaultValue = "asc") String sortBy,
			@RequestParam (defaultValue = "1")Integer pageNumber,
			@RequestParam (defaultValue = "10")Integer noOfRecords)
	{

		List<Product> list = product.getAllProducts(columnName,sortBy,pageNumber-1, noOfRecords);
		////System.out.println("Get All Products"+list.toString());
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}


	@PutMapping("/products/{id}")
	public MessageResponse updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {

		// Perform the update operation based on the id parameter
		boolean updated = product.updateProduct(id,updatedProduct);

		if (updated) {
			return new MessageResponse("Product updated successfully",201);
		} else {
			throw new ProductNotfoundException();
			///throw new Exception("Product not found");
		}
	}

	@DeleteMapping("/products/{id}")
	public MessageResponse deleteProduct(@PathVariable int id)
			throws Exception {

		// Perform the delete operation based on the id parameter
		boolean deleted = product.deleteProduct(id);

		if (deleted) {
			return new MessageResponse("Product Deleted successfully", 200);
		} else {
			throw new ProductNotfoundException();
		}
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String key) {
		List<Product> searchResults = product.searchProducts(key);

		if (!searchResults.isEmpty()) {
			return ResponseEntity.ok(searchResults);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
