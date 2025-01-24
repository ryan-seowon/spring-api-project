package com.seowoninfo.backend01.common.security;

import com.seowoninfo.backend01.member.entity.Member;
import com.seowoninfo.backend01.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final MemberRepository memberRepository;

	public CustomUserDetailsService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

		Member entity = memberRepository.findByMemberId(memberId);
		
		if(entity != null) {
			return new CustomUserDetails(entity);
		}
		
		return null;
	}

}
