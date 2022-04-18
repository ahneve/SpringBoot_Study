package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//데이터를 변경하는건 꼭 트랜지션이 있어야함
//스프링이 제공하는 트랜젝셔널 사용하기
//읽기에는 가급적이면 readOnly=true 넣어줌 => 데이터 변경 안됨
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    //autowired annotation은 스프링이 스프링 빈에 등록되어있는 memberRepository를 injection 해줌
    //@Autowired
    private final MemberRepository memberRepository;

    //생성자가 하나만 있는 경우 자동으로 injection을 해줌
    //    @Autowired
    //    public MemberService(MemberRepository memberRepository){
    //        this.memberRepository = memberRepository;
    //    }


    /*회원 가입*/
    //쓰기에는 readOnly = true가 아니니까 그냥 초기 transactional annotation 작성
    //(따로 설정한거는 우선권을 가짐)
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
