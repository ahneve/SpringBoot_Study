package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

//jpa의 내장타입이기 때문에
@Embeddable
//값타입은 기본적으로 변경되면 안되기 때문에 생성할 때만 값이 생성되게 하고, 세터는 제공하지 않는게 좋음.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //JPA 스펙상 엔티티나 임베디드 타입( @Embeddable )은 자바 기본 생성자(default constructor)를 public 또는 protected 로 설정해야 한다.
    protected Address(){

    }

    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
