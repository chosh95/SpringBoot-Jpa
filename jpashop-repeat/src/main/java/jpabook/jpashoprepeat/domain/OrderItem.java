package jpabook.jpashoprepeat.domain;

import jpabook.jpashoprepeat.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;
    private int count;

    //생성 메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setCount(count);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setItem(item);
        item.removeStock(count);
        return orderItem;
    }

    //비즈니스 로직
    public void cancel(){
        this.getItem().addStock(count);
    }

    public int getTotalPrice(){
        return getCount() * getOrderPrice();
    }
}
