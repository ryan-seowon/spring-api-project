package com.seowoninfo.backend01.member.repository;

import com.seowoninfo.backend01.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByMemberId(String memberId);
	
}
