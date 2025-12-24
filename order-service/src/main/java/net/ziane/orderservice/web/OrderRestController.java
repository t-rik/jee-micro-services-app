package net.ziane.orderservice.web;

import net.ziane.orderservice.entities.Order;
import net.ziane.orderservice.feign.CustomerRestClient;
import net.ziane.orderservice.feign.ProductRestClient;
import net.ziane.orderservice.repository.OrderRepository;
import net.ziane.orderservice.repository.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public OrderRestController(OrderRepository orderRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.orderRepository = orderRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order=orderRepository.findById(id).get();
        order.setCustomer(customerRestClient.getCustomerById(order.getCustomerId()));
        order.getProductItems().forEach(pi->{
            pi.setProduct(productRestClient.getProductById(pi.getProductId()));
        });
        return order;
    }
}
