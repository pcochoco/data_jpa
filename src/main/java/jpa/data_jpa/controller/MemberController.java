package jpa.data_jpa.controller;

import jakarta.annotation.PostConstruct;
import jpa.data_jpa.domain.Member;
import jpa.data_jpa.domain.MemberDto;
import jpa.data_jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//No Transaction 범위의 데이터 조회 -> 조회용으로만 사용
/*
    web 확장 - domain class converter
 */
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}") //pk를 통한 객체 조회
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}") //spring에서 중간과정 처리 -> 엔티티 객체를 바로 받아오는 방법
    public String findMember2(@PathVariable("id") Member member){
        return member.getUsername();
    }

    @PostConstruct
    public void init(){
        for(int i = 0; i < 30; i++){
            memberRepository.save(new Member("member" + i, i + 10));
        }
    }

    //paging and sorting
    @GetMapping("/members")
    public Page<MemberDto> List(Pageable pageable){ //Pageable interface -> PageRequest 생성해 사용
        return memberRepository.findAll(pageable)
            .map(member -> new MemberDto(member.getId(), member.getUsername(), null));

    }


    @GetMapping("/members/each")
    public Page<MemberDto> list2(
            //url을 설정 요청으로 받지 않고 개별 설정하는 경우
            @PageableDefault(
                    size = 12, sort = {"username"}, direction = Sort.Direction.ASC
            ) Pageable pageable){
        return memberRepository.findAll(pageable)
                .map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        //더 간단한 코드
        //.map(MemberDto::new);

    }




}
