package jpabook.jpashoprepeat.controller;

import jpabook.jpashoprepeat.Service.MemberService;
import jpabook.jpashoprepeat.domain.Address;
import jpabook.jpashoprepeat.domain.Member;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GeneratorType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createNewMember(@Valid @ModelAttribute("memberForm") MemberForm memberform, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(memberform.getCity(),memberform.getStreet(),memberform.getZipcode());
        Member member = new Member();
        member.setAddress(address);
        member.setName(memberform.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model){
        List<Member> memberList = memberService.findAll();
        model.addAttribute("members",memberList);
        return "members/memberList";
    }
}
