package jpabook.jpashoprepeat.Service;

import jpabook.jpashoprepeat.domain.Member;
import jpabook.jpashoprepeat.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원_가입() throws Exception{
        // given 특정 값이 주어질 때
        Member member = new Member();
        member.setName("Lee");
        // when 어떤 이벤트가 발생했을 때
        Long saveId = memberService.join(member);
        //then 그에 대한 결과를 보장해라.
        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_검사() throws Exception{
        // given 특정 값이 주어질 때
        Member member1 = new Member();
        member1.setName("park");

        Member member2 = new Member();
        member2.setName("park");
        // when 어떤 이벤트가 발생했을 때
        memberService.join(member1);
        memberService.join(member2);

        //then 그에 대한 결과를 보장해라.
        fail("예외가 발생해야 합니다.");

    }

}