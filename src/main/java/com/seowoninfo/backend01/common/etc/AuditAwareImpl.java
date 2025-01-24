package com.seowoninfo.backend01.common.etc;

import com.seowoninfo.backend01.common.security.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 등록/수정자정보 자동자정
 */
public class AuditAwareImpl implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication || !authentication.isAuthenticated()) {
			return null;
		}
		if("anonymousUser".equalsIgnoreCase(authentication.getName())) {
			return null;
		}
		CustomUserDetails detail = (CustomUserDetails) authentication.getPrincipal();
		return Optional.of(detail.getUsername());
	}
}