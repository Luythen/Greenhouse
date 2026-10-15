package com.github.Luythen.GreenHouseServer.Service;

import com.github.Luythen.GreenHouseServer.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* Username in this case is the users Email */
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Can't find a user with the email " + username));
    }

}
