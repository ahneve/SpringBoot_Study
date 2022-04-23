package jpabook.jpashop.service;

import jpabook.jpashop.domain.ae_01;
import jpabook.jpashop.repository.ae_01Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//런타임 시 해당 트랜잭션에 대한 최적화를 해줌
@Transactional(readOnly = true)
//final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
public class ae_01Service {

    private final ae_01Repository ae_01Repository;

    //텍스트 일기 저장
    @Transactional
    public void save_Text(ae_01 ae_01){
        ae_01Repository.text_save(ae_01);
    }

    //컬러링 이미지 저장
    @Transactional
    public void save_Coloring(ae_01 ae_01){
        ae_01Repository.coloring_save(ae_01);
    }

    //일기 조회 (이야기 해보고) 텍스트 일기 조회와 컬러링 일기 조회 두개로 분리
    public ae_01 findOne(Long diary_id){
        return ae_01Repository.findOne(diary_id);
    }
}
