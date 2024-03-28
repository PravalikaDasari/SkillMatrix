//package com.feuji.employeeservice.config;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import com.feuji.employeeservice.entity.UserLoginEntity;
//import com.feuji.employeeservice.repository.UserInfoRepository;
//
//@Component
//public class UserInfoUserDetailsService implements UserDetailsService {
//
//	@Autowired
//	private UserInfoRepository repository;
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Optional<UserLoginEntity> userInfo = repository.findByUserEmail(email);
//		return userInfo.map(UserInfoUserDetails::new)
//				.orElseThrow(() -> new UsernameNotFoundException("user not found " + email));
//
//	}
//}
