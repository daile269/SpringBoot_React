package com.example.springboot_reactjs.service.Impl;

import com.example.springboot_reactjs.entity.Account;
import com.example.springboot_reactjs.entity.AccountPrincipal;
import com.example.springboot_reactjs.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyAccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if(account == null) throw new UsernameNotFoundException("User not found");
        return new AccountPrincipal(account);
    }
}
