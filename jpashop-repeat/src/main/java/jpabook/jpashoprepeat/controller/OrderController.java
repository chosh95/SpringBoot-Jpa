package jpabook.jpashoprepeat.controller;

import jpabook.jpashoprepeat.Service.ItemService;
import jpabook.jpashoprepeat.Service.MemberService;
import jpabook.jpashoprepeat.Service.OrderService;
import jpabook.jpashoprepeat.domain.Member;
import jpabook.jpashoprepeat.domain.Order;
import jpabook.jpashoprepeat.domain.item.Item;
import jpabook.jpashoprepeat.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/order")
    public String order(Model model){

        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findAll();

        model.addAttribute("items",items);
        model.addAttribute("members",members);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId,itemId,count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orders(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
