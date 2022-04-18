package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue @Column(name="order_id")
    private Long id;

    //Order랑 Member는 다대일 관계 M:1=O:N
    @ManyToOne(fetch = FetchType.LAZY)
    //JoinColumn 어노테이션 사용하면 join key의 이름이 member_id가 되는 거임.
    @JoinColumn(name = "member_id")
    private Member member;

    //Java에서 객체는 변경사항이 있을 때 두 곳 다 변경해야 하지만
    //데이터베이스에서는 FK 하나만 변경하면 두 곳 동시에 변경이 됨.
    //이 간극을 극복하기 위해서 둘 중 하나를 '주인'이라는 개념으로 잡는데,
    //이를 '연관관계의 주인' 이라고 함.
    //Orders의 값을 바꿨을 때 Member의 값을 바꿀거야!! 하는 경우
    //연관관계의 주인은 FK와 가까운 곳으로 하면 됨
    //주인은 그대로 두고 주인이 아닌 쪽 (Member의 orders) 에 mapped로 설ㅔ걒ㅁ


    //CASCADE
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //one to one 관계에서는 fk 자체를 어디에 두어도 상관 없음.
    //접근이 많은 쪽에 두는게 좋음
    @OneToOne(fetch = FetchType.LAZY)
    //주인
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private Date date;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL)

    //==연관관계 편의 메서드==//

    //양방향 연관관계를 맺을 때에는, 양쪽 모두 관계를 맺어주어야한다.
    //사실 JPA의 입장에서 보았을 때에는 외래키 관리자(연관관계의 주인) 쪽에만 관계를 맺어준다면 정상적으로 양 쪽 모두에서 조회가 가능하다.
    //하지만 객체까지 고려한다면, 양쪽 다 관계를 맺어야한다.

    //원래 같았으면 main method 안에
    //Member member = new Member();
    //Order order  = new Order();
    //member.getOrders().add(order);
    //order.setMember(member);
    //이런식으로 작성했어야 함

    //하지만 이렇게 줄일 수 있음
    //추가로 연관관계 편의 메소드는 컨트롤 하는 쪽이 가지고 있는게 좋음
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
    * 주문 취소
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**전체 주문 가격 조회*/ public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }



}
