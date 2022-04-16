//회원 엔티티
package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
//lombok 쓰니까 getter setter 생성 대신 어노테이션으로 대체
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;


}
