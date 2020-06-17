package jpabook.jpashoprepeat;

import jpabook.jpashoprepeat.domain.Member;
import jpabook.jpashoprepeat.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testName() throws Exception{
        // given 특정 값이 주어질 때
        Member member = new Member();
        member.setUsername("kim");
        // when 어떤 이벤트가 발생했을 때
        Long saveId = memberRepository.save(member);
        Member member1 = memberRepository.find(saveId);
        //then 그에 대한 결과를 보장해라.
        Assertions.assertThat(member1.getId()).isEqualTo(saveId);
        Assertions.assertThat(member1.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(member1).isEqualTo(member);
    }
}