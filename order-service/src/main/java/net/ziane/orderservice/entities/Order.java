package net.ziane.orderservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ziane.orderservice.enums.OrderStatus;
import net.ziane.orderservice.model.Customer;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private OrderStatus status;
    private Long customerId;
    @Transient
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<ProductItem> productItems;

    @Transient
    public double getTotal() {
        double somme = 0;
        if(productItems!=null){
            for (ProductItem pi : productItems) {
                somme += pi.getAmount();
            }
        }
        return somme;
    }
}
