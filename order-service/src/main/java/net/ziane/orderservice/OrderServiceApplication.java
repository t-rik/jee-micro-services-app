package net.ziane.orderservice;

import net.ziane.orderservice.entities.Order;
import net.ziane.orderservice.entities.ProductItem;
import net.ziane.orderservice.enums.OrderStatus;
import net.ziane.orderservice.feign.CustomerRestClient;
import net.ziane.orderservice.feign.ProductRestClient;
import net.ziane.orderservice.model.Customer;
import net.ziane.orderservice.model.Product;
import net.ziane.orderservice.repository.OrderRepository;
import net.ziane.orderservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(OrderRepository orderRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient){
        return args -> {
            try {
                List<Customer> customers = customerRestClient.getAllCustomers().getContent().stream().toList();
                List<Product> products = productRestClient.getAllProducts().getContent().stream().toList();
                Long customerId = customers.get(0).getId();
                Random random = new Random();
                for(int i=0; i<50; i++){
                    Order order = Order.builder()
                            .customerId(customers.get(random.nextInt(customers.size())).getId())
                            .status(Math.random()>0.5? OrderStatus.PENDING:OrderStatus.CREATED)
                            .createdAt(new Date())
                            .build();
                    Order savedOrder = orderRepository.save(order);
                    for(int j=0; j<products.size(); j++){
                        if(Math.random()>0.70){
                            ProductItem productItem = ProductItem.builder()
                                    .order(savedOrder)
                                    .productId(products.get(j).getId())
                                    .price(products.get(j).getPrice())
                                    .quantity(1+random.nextInt(10))
                                    .discount(Math.random())
                                    .build();
                            productItemRepository.save(productItem);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer repositoryRestConfigurer() {
        return org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Order.class);
        });
    }

}
