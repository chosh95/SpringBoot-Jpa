package jpabook.jpashoprepeat.Service;

import jpabook.jpashoprepeat.domain.item.Item;
import jpabook.jpashoprepeat.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void join(Item item){
        itemRepository.save(item);
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }
}
