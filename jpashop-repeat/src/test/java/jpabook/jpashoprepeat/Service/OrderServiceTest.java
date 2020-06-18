package jpabook.jpashoprepeat.Service;

import jpabook.jpashoprepeat.domain.Address;
import jpabook.jpashoprepeat.domain.Member;
import jpabook.jpashoprepeat.domain.Order;
import jpabook.jpashoprepeat.domain.OrderStatus;
import jpabook.jpashoprepeat.domain.item.Book;
import jpabook.jpashoprepeat.domain.item.Item;
import jpabook.jpashoprepeat.exception.NotEnoughStockException;
import jpabook.jpashoprepeat.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 상품주문() throws Exception{
        // given 특정 값이 주어질 때
        Member member =createMember();
        Book book = createBook("JPA", 10000, 10);
        int orderCount = 2;

        // when 어떤 이벤트가 발생했을 때
        Long orderId = orderService.order(member.getId(),book.getId(),orderCount);
        Order getOrder = orderRepository.findOne(orderId);
        //then 그에 대한 결과를 보장해라.
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER,getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야한다.",1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.",10000 * 2,getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다.",10 -2,book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        // given 특정 값이 주어질 때
        Member member = createMember();
        Item book = createBook("JPA",1000,20);

        int orderCount = 22;
        // when 어떤 이벤트가 발생했을 때
        orderService.order(member.getId(),book.getId(),orderCount);

        //then 그에 대한 결과를 보장해라.
        fail("여기에 도달하면 안된다.");

    }

    @Test
    public void 주문취소() throws Exception{
        // given 특정 값이 주어질 때
        Member member= createMember();
        Item book = createBook("JPA",1000,10);
        int orderCount = 2;

        // when 어떤 이벤트가 발생했을 때
        Long orderId = orderService.order(member.getId(),book.getId(),orderCount);
        orderService.cancelOrder(orderId);
        Order getOrder = orderRepository.findOne(orderId);
        //then 그에 대한 결과를 보장해라.
        assertEquals("주문 취소시 상태는 CANCEL",OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("주문 취소시 재고가 복구되어야 한다.",10,book.getStockQuantity());
    }
    private Member createMember(){
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울","헌릉로","123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}