package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//상속관계 전략을 지정해야 하는데 이 전략은 부모 클래스에서 잡아주어야 함.
//우리는 싱글테이블 전략을 쓰니까 이걸로 잡아줌
//*궁금해서 찾아봤는데 어노테이션 순서는 실행 순서에 영향을 주지 않음
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
@Getter
@Setter
public class Item {

    //Item은 Album, Book, Movie 세가지 상속관계 매핑을 해야함
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;


    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //도메인 주도 설계: 엔티티 자체가 해결할 수 있는 것들은 엔티티 안에 비즈니스 로직을 넣는것이 좋음
    //==비즈니스 로직==//

    /**
     *  stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     *  stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
    this.stockQuantity = restStock;
    }
}
