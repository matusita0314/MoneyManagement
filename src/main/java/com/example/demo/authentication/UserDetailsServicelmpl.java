package com.example.demo.authentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Spring securityの認証機能をカスタマイズするためのクラス
 * DBからユーザー情報をとってきてログイン認証できるようにする。
 * （元々のSpringSecurityではuserがユーザー名でパスワードは提供される）
 */

@Component
@RequiredArgsConstructor
public class UserDetailsServicelmpl implements UserDetailsService {
	
	/** ユーザー情報テーブルRepository */
	private final UserRepository repository;
	
	/**
	 * ユーザー情報生成
	 * 
	 * @Param username ログインID
	 * @throws UsernameNotFoundExceprion
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
				//ユーザーが見つからない場合はUsernameNotFoundExceptionをスロー
		
		return User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.build();
	}

}
