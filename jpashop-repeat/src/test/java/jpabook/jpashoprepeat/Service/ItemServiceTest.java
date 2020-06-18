package jpabook.jpashoprepeat.Service;

import jpabook.jpashoprepeat.domain.item.Book;
import jpabook.jpashoprepeat.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Test
    public void 아이템_추가() throws Exception{
        // given 특정 값이 주어질 때
        Book book = new Book();
        book.setAuthor("han");
        book.setName("kim");
        // when 어떤 이벤트가 발생했을 때
        itemService.join(book);
        //then 그에 대한 결과를 보장해라.
        assertEquals(book,itemService.findOne(book.getId()));
    }
}