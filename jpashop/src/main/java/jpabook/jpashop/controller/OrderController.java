package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String create(@RequestParam("memberId") Long memberId,
                         @RequestParam("itemId") Long itemId,
                         @RequestParam("count") int count){
        orderService.order(memberId,itemId,count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model){

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @GetMapping("/orders2")
    public String orderList2(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model){

        List<OrderDto> orderList = new ArrayList<>();
        List<Order> orders = orderService.findOrders(orderSearch);
        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderDto dto = new OrderDto();
                dto.setItemName(orderItem.getItem().getName());
                dto.setMemberName(order.getMember().getName());
                dto.setId(order.getId());
                dto.setOrderDate(order.getOrderDate());
//                dto.setOrderPrice(orderItem.getItem().getPrice());
                dto.setOrderPrice(orderItem.getOrderPrice());
                dto.setStatus(order.getStatus());
                dto.setCount(orderItem.getCount());
                orderList.add(dto);
            }
        }
        for (OrderDto orderDto : orderList) {
            System.out.println(orderDto.getId() + " " + orderDto.getItemName() + " " + orderDto.getOrderPrice());
        }

        model.addAttribute("orders",orderList);
        return "order/orderList2";
    }

    @Data
    @Getter @Setter
    static class OrderDto{
        private Long id;
        private String memberName;
        private String itemName;
        private int orderPrice;
        private int count;
        private OrderStatus status;
        private LocalDateTime orderDate;
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
