package com.example.springboot_reactjs.service.Impl;

import com.example.springboot_reactjs.dto.AccountDTO;
import com.example.springboot_reactjs.dto.request.AuthenticationRequest;
import com.example.springboot_reactjs.dto.response.AuthenticationResponse;
import com.example.springboot_reactjs.entity.Account;
import com.example.springboot_reactjs.exception.AppException;
import com.example.springboot_reactjs.exception.ErrorCode;
import com.example.springboot_reactjs.repositories.AccountRepository;
import com.example.springboot_reactjs.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Override
    public AccountDTO saveAccount(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        Account saveAccount = accountRepository.save(account);
        return accountToDTO(account);
    }

    @Override
    public void checkUsernameExist(String username) {
        Account account = accountRepository.findByUsername(username);
        if(account!=null) throw new AppException(ErrorCode.ACCOUNT_IS_EXISTS);
    }

    @Override
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<AccountDTO> getAllAccount() {
        List<AccountDTO> result = accountRepository.findAll().stream()
                .map(account -> accountToDTO(account)).collect(Collectors.toList());
        return result;
    }

    @Override
    @PostAuthorize("returnObject.username==authentication.name or hasAuthority('ADMIN')")
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_IS_NOT_EXISTS));
        return accountToDTO(account);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_IS_NOT_EXISTS));
        accountRepository.deleteById(id);
    }

    @Override
    public AuthenticationResponse verify(AuthenticationRequest auth) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(),auth.getPassword()));
        if(!authentication.isAuthenticated()) throw new AppException(ErrorCode.UNAUTHENTICATED);
        String tokenResponse = jwtService.generateToken(auth.getUsername());
       return AuthenticationResponse.builder()
               .token(tokenResponse)
               .authenticated(true).build();
    }

    @Override

    public AccountDTO getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(username);
        return accountToDTO(account);
    }

    private AccountDTO accountToDTO(Account account){
        return modelMapper.map(account,AccountDTO.class);
    }
}
