package id.co.PPMToolFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import id.co.PPMToolFullStack.domain.User;
import id.co.PPMToolFullStack.exceptions.UsernameAlreadyExistsException;
import id.co.PPMToolFullStack.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User newUser) {
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			// Username has to be unique (exception)
			newUser.setUsername(newUser.getUsername());
			newUser.setConfirmPassword("");
			// make sure that password and confirmPassword match
			// we don't persist or show the confirmPassword
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
		}

	}

}
