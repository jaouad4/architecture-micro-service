package ma.jaouad.billingservice;

import ma.jaouad.billingservice.entities.Bill;
import ma.jaouad.billingservice.entities.ProductItem;
import ma.jaouad.billingservice.repositories.BillRepository;
import ma.jaouad.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BillingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository) {
        return args -> {
            // Créer des factures de test
            Bill bill1 = billRepository.save(new Bill(null, new Date(), 1L, null, null));
            productItemRepository.saveAll(List.of(
                    new ProductItem(null, 1L, null, 5000.0, 2, bill1),
                    new ProductItem(null, 2L, null, 1200.0, 1, bill1)
            ));

            Bill bill2 = billRepository.save(new Bill(null, new Date(), 2L, null, null));
            productItemRepository.saveAll(List.of(
                    new ProductItem(null, 3L, null, 3500.0, 1, bill2),
                    new ProductItem(null, 4L, null, 150.0, 3, bill2)
            ));

            Bill bill3 = billRepository.save(new Bill(null, new Date(), 3L, null, null));
            productItemRepository.saveAll(List.of(
                    new ProductItem(null, 1L, null, 5000.0, 1, bill3),
                    new ProductItem(null, 3L, null, 3500.0, 2, bill3)
            ));

            System.out.println("Bills created successfully!");
        };
    }
}