package jpabook.jpashoprepeat;

import jpabook.jpashoprepeat.Service.MemberService;
import jpabook.jpashoprepeat.domain.Member;
import jpabook.jpashoprepeat.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopRepeatApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopRepeatApplication.class, args);
	}

}
