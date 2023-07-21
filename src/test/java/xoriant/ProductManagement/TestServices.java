package xoriant.ProductManagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import xoriant.ProductManagement.model.Product;
import xoriant.ProductManagement.repository.ProductRepository;
import xoriant.ProductManagement.serviceImpl.ProductServiceImpl;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class TestServices {

	@Mock
	private ProductRepository prodRepository;

	@InjectMocks
	private ProductServiceImpl productService = new ProductServiceImpl(prodRepository);

	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	public void TestCreateProduct() {
		Product prod = new Product();
		prod.setId(1);
		prod.setName("Gaming mouse");
		prod.setCategory("Electronics");
		prod.setCreated_at(LocalDateTime.now());
		prod.setDescription("its a gaming mouse");
		prod.setPrice(100);
		prod.setQuantity(20);
		prod.setStatus("Active");
		prod.setVendor("Amazon");
		prod.setUpdated_at(null);

		when(prodRepository.save(any(Product.class))).thenReturn(prod);
		Product createdProduct = productService.createProduct(prod);
		assertEquals(prod, createdProduct);
	}

	@Test
	@Order(2)
	public void TestGetOneProduct() {
		int productId = 1;
		Product prod = new Product();
		prod.setId(productId);

		when(prodRepository.findById(productId)).thenReturn(Optional.of(prod));

		Optional<Product> retrievedProduct = productService.getProductById(productId);

		verify(prodRepository, times(1)).findById(productId);
		assertTrue(retrievedProduct.isPresent());
		assertEquals(prod, retrievedProduct.get());
	}

	@Test
	@Order(3)
	public void TestUpdateProduct() {
		int productId = 1;
		Product existingProduct = new Product();
		existingProduct.setId(productId);
		existingProduct.setName("Existing Product");

		Product updatedProduct = new Product();
		updatedProduct.setId(productId);
		updatedProduct.setName("Updated Product");

		when(prodRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
		when(prodRepository.save(any(Product.class))).thenReturn(updatedProduct);

		boolean isUpdated = productService.updateProduct(productId, updatedProduct);

		verify(prodRepository, times(1)).findById(productId);
		verify(prodRepository, times(1)).save(any(Product.class));
		assertTrue(isUpdated);
	}

	@Test
	@Order(4)
	public void TestDeleteProduct() {
		int productId = 1;
		Product prod = new Product();
		prod.setId(productId);

		when(prodRepository.findById(productId)).thenReturn(Optional.of(prod));

		boolean isDeleted = productService.deleteProduct(productId);

		verify(prodRepository, times(1)).findById(productId);
		verify(prodRepository, times(1)).delete(prod);
		assertTrue(isDeleted);
	}

}
