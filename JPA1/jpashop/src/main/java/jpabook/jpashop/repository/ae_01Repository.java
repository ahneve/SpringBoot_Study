package jpabook.jpashop.repository;


import jpabook.jpashop.domain.ae_01;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//1. 텍스트 일기 등록              //4. 컬러링 일기 등록
//2. 텍스트 일기 조회              //5. 컬러링 일기 조회
//3. 텍스트 일기 수정              //6. 컬러링 일기 수정

//스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 변환
@Repository
public class ae_01Repository {

    //앤티티 매니저 주입
    @PersistenceContext
    private EntityManager em;

    //텍스트 일기 등록, 수정
    //git 레포지토리 옮긴 다음에 이름 수정
    public void text_save(ae_01 ae_01) {
        //diary_id가 없으면 신규로 보고 persist()시행
        //(이야기 해보고) text_id로 수정
        if(ae_01.getDiary_id() == null) {
            em.persist(ae_01.getText());
        } else { //diary_id가 있으면 저장된 엔티티를 수정한다고 보고 merge()를 실행
            em.merge(ae_01.getText());
        }
    }

    //텍스트랑 컬러링 아이디 따로 두는게 효율적이지 않을까? 메모리 구현할 때도 마찬가지
    //컬러링 일기 등록, 수정
    public void coloring_save(ae_01 ae_01) {
        //diary_id가 없으면 신규로 보고 persist() 시행
        //(이야기 해보고) coloring_id로 수정
        if(ae_01.getDiary_id() == null) {
            em.persist(ae_01.getImage());
        } else { //diary_id가 있으면 저장된 엔티티를 수정한다고 보고 merge()를 실행
            em.merge(ae_01.getImage());
        }
    }

    //일기 조회 (이야기 해보고) 텍스트 일기 조회와 컬러링 일기 조회 두개로 분리
    public ae_01 findOne(Long diary_id) {
        return em.find(ae_01.class, diary_id);
    }

}
