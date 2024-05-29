package jpa.data_jpa.controller;

import jakarta.annotation.PostConstruct;
import jpa.data_jpa.domain.Member;
import jpa.data_jpa.domain.MemberDto;
import jpa.data_jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//No Transaction -> 조회용으로만 사용
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}") //pk 객체 조회
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}") //spring 중간과정 처리 -> 엔티티 객체를 바로 받아오는 방법
    public String findMember2(@PathVariable("id") Member member){
        return member.getUsername();
    }

    @PostConstruct
    public void init(){
        for(int i = 0; i < 30; i++){
            memberRepository.save(new Member("member" + i, i + 10));
        }
    }

    @GetMapping("/members")
    public Page<MemberDto> List(Pageable pageable){ //PageRequest 생성해 사용
        return memberRepository.findAll(Pageable pageable){
            return memberRepository.findAll(pageable)
                    .map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        }
    }



}
