package com.store.passmeby.PassMeByApi.service;

import com.store.passmeby.PassMeByApi.dao.CustomerDao;
import com.store.passmeby.PassMeByApi.dao.vo.Users;
import com.store.passmeby.PassMeByApi.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userDao.findByUsername(email);
        if (users == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }

         return new UserDetailsImpl(users);
    }
}