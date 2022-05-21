package com.dan.cosmos.security;

import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AppUser appUser = userRepository.findByUsername(username);

        if (appUser == null) {
            throw new RuntimeException("User not found");
        }

        MyUserDetails myUserDetails = new MyUserDetails(username,
                appUser.getPassword(),
                appUser.isEnabled(),
                true,
                true,
                !appUser.isLocked(),
                appUser.getAppUserRoles());

        myUserDetails.setAppUser(appUser);
        return myUserDetails;

        /*return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(appUser.getPassword())
                .authorities(appUser.getAppUserRoles())
                .accountExpired(false)
                .accountLocked(appUser.isLocked())
                .credentialsExpired(false)
                .disabled(!appUser.isEnabled())
                .build();*/

    }
}
