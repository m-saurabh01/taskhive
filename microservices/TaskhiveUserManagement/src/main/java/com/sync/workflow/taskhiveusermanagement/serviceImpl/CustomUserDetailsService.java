package com.sync.workflow.taskhiveusermanagement.serviceImpl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sync.workflow.taskhiveusermanagement.entity.User;
import com.sync.workflow.taskhiveusermanagement.repository.UserRepository;
import com.sync.workflow.taskhiveusermanagement.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


		Optional<User> user = userRepository.findByEmail(email);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with email " + email);
		}

		

		return new CustomUserDetails(user.get());
	}
}
