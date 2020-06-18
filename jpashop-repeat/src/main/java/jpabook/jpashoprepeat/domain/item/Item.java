package jpabook.jpashoprepeat.domain.item;

import jpabook.jpashoprepeat.domain.Category;
import jpabook.jpashoprepeat.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //비즈니스 로직
    public void addStock(int amount){
        this.stockQuantity += amount;
    }

    public void removeStock(int amount){
        if(this.stockQuantity - amount < 0 ){
            throw new NotEnoughStockException("need more stock");
        }
        else this.stockQuantity -= amount;
    }
}
