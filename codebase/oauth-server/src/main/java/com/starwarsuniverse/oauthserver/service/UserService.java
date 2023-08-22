package com.starwarsuniverse.oauthserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.starwarsuniverse.oauthserver.models.Account;
import com.starwarsuniverse.oauthserver.models.User;
import com.starwarsuniverse.oauthserver.repositories.UserRepository;

@Service(value = "userService")
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username); //email and username are same 

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Incorrect Username / Password supplied!");
        }

        Account account = user.get().getAccount();

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(account.getPassword())
                .authorities(account.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
