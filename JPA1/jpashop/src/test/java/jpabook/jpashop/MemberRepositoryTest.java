package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

//스프링부트로 테스트 할거란걸 알려주는 어노테이션
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    //autowired 어노테이션으로 memerRepository injection 받음
    @Autowired MemberRepository memberRepository;

    @Test
    //entity manager은 항상 트랜잭션 안에서 실행되어야함
    //springframework의 transactional 어노테이션 쓰기를 권장

    //Transactional 어노테이션이 테스트케이스에 있으면 테스트가 끝난다음에 바로 롤백을 해버림
    //데이터가 들어있으면 반복적인 테스트를 못하니까
    @Transactional
    //롤백=false로 두고 테스트 해보셈
    @Rollback(false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        //member.setUsername("memberA");

        //when
        /*변수 extract 단축키:  opt+com+v */
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then(검증)
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        //Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
    }


    
}