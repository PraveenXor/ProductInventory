package xoriant.ProductManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import xoriant.ProductManagement.controllers.ProductManagementController;
import xoriant.ProductManagement.model.Product;
import xoriant.ProductManagement.response.MessageResponse;
import xoriant.ProductManagement.services.ProductService;

public class TestController {

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductManagementController productManagementController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Order(1)
	public void testCreateProduct() throws Exception {
		Product product = new Product();
		product.setId(1);
		product.setName("Sample Product");
		product.setName("Gaming mouse");
		product.setCategory("Electronics");
		product.setCreated_at(LocalDateTime.now());
		product.setDescription("its a gaming mouse");
		product.setPrice(100);
		product.setQuantity(20);
		product.setStatus("Active");
		product.setVendor("Amazon");
		product.setUpdated_at(null);

		when(productService.createProduct(any(Product.class))).thenReturn(product);

		ResponseEntity<Product> response = productManagementController.createProduct(product);

		verify(productService, times(1)).createProduct(any(Product.class));
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(product, response.getBody());
	}

	@Test
	@Order(2)
	public void testGetProductById() {
		int productId = 1;
		Product product = new Product();
		product.setId(productId);
		product.setName("Sample Product-2");
		product.setName("Gaming mouse");
		product.setCategory("Electronics");
		product.setCreated_at(LocalDateTime.now());
		product.setDescription("its a gaming mouse");
		product.setPrice(800);
		product.setQuantity(20);
		product.setStatus("Active");
		product.setVendor("Flipkart");
		product.setUpdated_at(null);

		when(productService.getProductById(productId)).thenReturn(Optional.of(product));

		ResponseEntity<Optional<Product>> response = productManagementController.getProdById(productId);

		verify(productService, times(1)).getProductById(productId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(product, response.getBody().get());
	}

	@Test
	@Order(3)
	public void testGetAllProducts() {
		List<Product> productList = new ArrayList<>();

		int productId = 1;
		int productId1 = 2;
		Product product = new Product();
		product.setId(productId);
		product.setName("Sample Product-2");
		product.setName("Gaming mouse");
		product.setCategory("Electronics");
		product.setCreated_at(LocalDateTime.now());
		product.setDescription("its a gaming mouse");
		product.setPrice(800);
		product.setQuantity(20);
		product.setStatus("Active");
		product.setVendor("Flipkart");
		product.setUpdated_at(null);

		Product product1 = new Product();
		product1.setId(productId1);
		product1.setName("Sample Product-3");
		product1.setName("Gaming mouse");
		product1.setCategory("Electronics");
		product1.setCreated_at(LocalDateTime.now());
		product1.setDescription("its a gaming mouse with advanced technology");
		product1.setPrice(5000);
		product1.setQuantity(76);
		product1.setStatus("Active");
		product1.setVendor("Red Gear");
		product1.setUpdated_at(null);

		productList.add(product);
		productList.add(product1);

		when(productService.getAllProducts(anyString(), anyString(), anyInt(), anyInt())).thenReturn(productList);

		ResponseEntity<List<Product>> response = productManagementController.getAllProduct("name", "asc", 1, 10);

		verify(productService, times(1)).getAllProducts(anyString(), anyString(), anyInt(), anyInt());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(productList, response.getBody());
	}

	/*
	 * @Test
	 * 
	 * @Order(4) public void testUpdateProduct_Success() throws Exception {
	 * 
	 * int productId = 1; Product existingProduct = new Product();
	 * existingProduct.setId(productId);
	 * existingProduct.setName("Existing Product");
	 * 
	 * Product updatedProduct = new Product(); updatedProduct.setId(productId);
	 * updatedProduct.setDescription("Updated Product");
	 * when(productService.updateProduct(eq(productId),
	 * refEq(updatedProduct))).thenReturn(true);
	 * 
	 * MessageResponse expectedResponse = new
	 * MessageResponse("Product updated successfully", 201); MessageResponse
	 * response = productManagementController.updateProduct(productId,
	 * updatedProduct);
	 * 
	 * verify(productService, times(1)).updateProduct(eq(productId),
	 * refEq(updatedProduct)); assertEquals(expectedResponse, response); }
	 */

	@Test
	@Order(4)
	public void testUpdateProduct_ProductNotFound() throws Exception {
		int productId = 1;

		Product updatedProduct = new Product();
		updatedProduct.setName("Updated Product");
		when(productService.updateProduct(productId, updatedProduct)).thenReturn(false);

		assertThrows(Exception.class, () -> {
			productManagementController.updateProduct(productId, updatedProduct);
		});

		verify(productService, times(1)).updateProduct(productId, updatedProduct);
	}

	@Test
	@Order(6)
	public void testDeleteProduct_ProductNotFound() throws Exception {
		int productId = 1;

		when(productService.deleteProduct(productId)).thenReturn(false);

		assertThrows(Exception.class, () -> {
			productManagementController.deleteProduct(productId);
		});

		verify(productService, times(1)).deleteProduct(productId);
	}

	@Test
	@Order(5)
	public void testSearchProducts() {
		String key = "sample";
		List<Product> searchResults = new ArrayList<>();

		int productId1 = 2;
		Product product1 = new Product();
		product1.setId(productId1);
		product1.setName("Sample Product-3");
		product1.setName("Gaming mouse");
		product1.setCategory("Electronics");
		product1.setCreated_at(LocalDateTime.now());
		product1.setDescription("its a gaming mouse with advanced technology");
		product1.setPrice(5000);
		product1.setQuantity(76);
		product1.setStatus("Active");
		product1.setVendor("Red Gear");
		product1.setUpdated_at(null);

		searchResults.add(product1);

		when(productService.searchProducts(key)).thenReturn(searchResults);

		ResponseEntity<List<Product>> response = productManagementController.searchProducts(key);

		verify(productService, times(1)).searchProducts(key);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(searchResults, response.getBody());
	}
}
