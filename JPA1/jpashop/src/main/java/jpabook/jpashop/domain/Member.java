package jpabook.jpashop.domain;
//엔티티

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//lombok으로 Getter랑 Setter 열어주기
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    //Order의 Pk가 member_id로 들어가 있기 때문에
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    //Members랑 서로 적어정
    //Order 테이블에 있는 member 에 의해 매핑 된 거울이다. 라는 뜻!
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<> ();

}
