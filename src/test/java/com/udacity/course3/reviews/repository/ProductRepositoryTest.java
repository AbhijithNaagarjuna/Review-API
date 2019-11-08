package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired private TestEntityManager testEntityManager;
    @Autowired private ProductRepository productRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(testEntityManager).isNotNull();
        assertThat(productRepository).isNotNull();
    }

    @Test
    public void testCreateProduct(){
        Product product = new Product("iPhone 11",
                "A new dual-camera system captures more of what you see and love.");
        productRepository.save(product);

        Product fromDb = productRepository.findById(product.getProdId()).orElse(null);
        assertThat(fromDb.getProdId()).isEqualTo(product.getProdId());

        productRepository.delete(product); // clean up
    }

    @Test
    public void testFindById(){
        Product product = new Product("iPhone 11",
                "A new dual-camera system captures more of what you see and love.");
        testEntityManager.persistAndFlush(product);

        Product fromDb = productRepository.findById(product.getProdId()).orElse(null);
        assertThat(fromDb.getProdId()).isEqualTo(product.getProdId());
    }

    @Test
    public void testFindAll(){
        Product product1 = new Product("iPhone 11",
                "A new dual-camera system captures more of what you see and love.");
        Product product2 = new Product("iPhone XS Max",
                "Welcome to the big screens.  Super retina.  Even faster Face ID.");

        testEntityManager.persist(product1);
        testEntityManager.persist(product2);
        testEntityManager.flush();

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(2).extracting(Product::getProdName)
            .containsOnly(product1.getProdName(), product2.getProdName());
    }

}
