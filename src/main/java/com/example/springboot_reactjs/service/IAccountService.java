package com.example.springboot_reactjs.service;

import com.example.springboot_reactjs.dto.AccountDTO;
import com.example.springboot_reactjs.dto.request.AuthenticationRequest;
import com.example.springboot_reactjs.dto.response.AuthenticationResponse;
import com.example.springboot_reactjs.entity.Account;

import java.util.List;

public interface IAccountService {
    AccountDTO saveAccount(Account account);

    void checkUsernameExist(String username);
    List<AccountDTO> getAllAccount();

    AccountDTO getAccountById(Long id);

    void deleteAccount(Long id);

    AuthenticationResponse verify(AuthenticationRequest auth);

    AccountDTO getMyInfo();
}
