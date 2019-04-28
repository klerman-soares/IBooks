package com.klerman.ibooks.auth;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }
    
    @Override
    public User save(User user) {
    	return userRepository.save(user);
    }
}
