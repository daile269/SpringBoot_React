package com.example.springboot_reactjs.controller.admin;

import com.example.springboot_reactjs.dto.AccountDTO;
import com.example.springboot_reactjs.entity.Account;
import com.example.springboot_reactjs.exception.AppException;
import com.example.springboot_reactjs.exception.ErrorCode;
import com.example.springboot_reactjs.service.IAccountService;
import com.example.springboot_reactjs.service.Impl.MyAccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/accounts")
public class AccountController {

    private final IAccountService accountService;

    @PostMapping
    @ResponseBody
    public AccountDTO createAccount(@RequestBody Account account){
        accountService.checkUsernameExist(account.getUsername());
        return accountService.saveAccount(account);
    }

    @GetMapping
    @ResponseBody
    public List<AccountDTO> getAllAccount(){
        return accountService.getAllAccount();
    }
    @GetMapping(value = "/{accountId}")
    @ResponseBody
    public AccountDTO getAccountById(@PathVariable Long accountId){
        return accountService.getAccountById(accountId);
    }

    @PutMapping(value = "/{accountId}")
    @ResponseBody
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody Account account, @PathVariable Long accountId){
        AccountDTO existAccount = accountService.getAccountById(accountId);
        if(existAccount == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        account.setId(accountId);
        AccountDTO updateAccount = accountService.saveAccount(account);
        return ResponseEntity.ok(updateAccount);
    }

    @DeleteMapping(value = "/{accountId}")
    @ResponseBody
    public List<AccountDTO> deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
        return accountService.getAllAccount();
    }


}
