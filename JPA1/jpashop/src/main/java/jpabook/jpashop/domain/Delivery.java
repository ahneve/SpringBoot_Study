package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //Order랑 관계를 가지니까
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    //Enumerated 어노테이션을 사용하면 자바 enum 타입을 엔티티 클래스의 속성으로 사용할 수 있다.
    //예외가 있을 수 있기 때문에 꼭 enumtype을 string으로 써야함
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
