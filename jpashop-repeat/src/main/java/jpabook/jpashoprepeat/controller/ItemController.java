package jpabook.jpashoprepeat.controller;

import jpabook.jpashoprepeat.Service.ItemService;
import jpabook.jpashoprepeat.domain.item.Book;
import jpabook.jpashoprepeat.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItem(Model model){
        model.addAttribute("form",new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(@Valid ItemForm form, BindingResult result){
        if(result.hasErrors()){
            return "items/createItemForm";
        }
        Book book = new Book();
        book.setName(form.getName());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

        itemService.join(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String itemList(Model model){
        List<Item> items = itemService.findAll();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String update(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book)itemService.findOne(itemId);
        ItemForm form = new ItemForm();
        form.setAuthor(item.getAuthor());
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String update(@PathVariable("itemId") Long itemId, @Valid @ModelAttribute("form") ItemForm form, BindingResult result){
        if(result.hasErrors()){
            return "items/{itemId}/edit";
        }
        Book book = new Book();
        book.setIsbn(form.getIsbn());
        book.setStockQuantity(form.getStockQuantity());
        book.setPrice(form.getPrice());
        book.setAuthor(form.getAuthor());
        book.setName(form.getName());
        book.setId(form.getId());
        itemService.join(book);
        return "redirect:/items";
    }
}
