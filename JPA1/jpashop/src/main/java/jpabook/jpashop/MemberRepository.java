package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    //entity: 객체와 rdb를 연결시켜주는 존재
    //entity Manager: 엔티티를 관리하는 역할, 여러 엔티티 매니저가 하나의 영속성 컨텍스트를 공유 가
    //jpa를 쓰기 때문에 entity 매니저가 있어야함
    //entitymanager 주입해주는 어노테이션
    @PersistenceContext
    private EntityManager em;

    //저장하는 코드
    //cmd+shift+t: test 파일 생성
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
