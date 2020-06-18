package jpabook.jpashoprepeat.Service;

import jpabook.jpashoprepeat.domain.*;
import jpabook.jpashoprepeat.domain.item.Item;
import jpabook.jpashoprepeat.repository.ItemRepository;
import jpabook.jpashoprepeat.repository.MemberRepository;
import jpabook.jpashoprepeat.repository.OrderRepository;
import jpabook.jpashoprepeat.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    @Transactional
    public Long order(Long memberId, Long ItemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemService.findOne(ItemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(), count);

        Order order = Order.createOrder(member,delivery,orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
