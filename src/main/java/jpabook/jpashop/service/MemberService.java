package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	/**
	 * 회원 가입
	 *
	 * @param member
	 * @return
	 */
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member); //중복 회원 검증

		memberRepository.save(member);

		return member.getId();
	}

	//멤버수가 0보다 크다고 하는게 최적화 하기 좋을듯
	// DB에 member에 name제약조건을 unique로 주면 동시에 가입되는 것을 최후방지 할 수 있음
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()){
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}

	}

	//회원 전체 조회
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}

}
