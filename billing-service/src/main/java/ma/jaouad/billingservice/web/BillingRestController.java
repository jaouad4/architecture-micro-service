package ma.jaouad.billingservice.web;

import ma.jaouad.billingservice.entities.Bill;
import ma.jaouad.billingservice.feign.CustomerRestClient;
import ma.jaouad.billingservice.feign.ProductRestClient;
import ma.jaouad.billingservice.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillingRestController {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CustomerRestClient customerRestClient;
    @Autowired
    private ProductRestClient productRestClient;

    @GetMapping("/bills/{id}")
    public Bill getBill(@PathVariable Long id) {
        Bill bill = billRepository.findById(id).orElse(null);
        if (bill != null) {
            bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
            bill.getProductItems().forEach(pi -> {
                pi.setProduct(productRestClient.getProductById(pi.getProductId()));
            });
        }
        return bill;
    }
}
