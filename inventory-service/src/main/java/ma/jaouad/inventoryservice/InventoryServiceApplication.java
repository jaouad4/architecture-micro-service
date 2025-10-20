package ma.jaouad.inventoryservice;

import ma.jaouad.inventoryservice.entities.Product;
import ma.jaouad.inventoryservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.saveAll(List.of(
                    Product.builder().name("Ordinateur").price(5000.0).quantity(10).build(),
                    Product.builder().name("Imprimante").price(1200.0).quantity(5).build(),
                    Product.builder().name("Smartphone").price(3500.0).quantity(20).build(),
                    Product.builder().name("Clavier").price(150.0).quantity(50).build()
            ));
            productRepository.findAll().forEach(System.out::println);
        };
    }

}
