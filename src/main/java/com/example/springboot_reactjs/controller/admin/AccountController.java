package com.example.springboot_reactjs.controller.admin;

import com.example.springboot_reactjs.dto.AccountDTO;
import com.example.springboot_reactjs.dto.response.ApiResponse;
import com.example.springboot_reactjs.entity.Account;
import com.example.springboot_reactjs.service.IAccountService;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<AccountDTO> createAccount(@RequestBody Account account){
        accountService.checkUsernameExist(account.getUsername());
        accountService.saveAccount(account);
        return ApiResponse.<AccountDTO>builder()
                .code(200)
                .message("Tạo acccount thành công")
                .build();
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
    public ApiResponse<AccountDTO> updateAccount(@RequestBody Account account, @PathVariable Long accountId){
        AccountDTO existAccount = accountService.getAccountById(accountId);
        if(existAccount == null) return ApiResponse.<AccountDTO>builder()
                .code(200)
                .message("Account không tồn tại")
                .build();;
        account.setId(accountId);
        accountService.saveAccount(account);
        return ApiResponse.<AccountDTO>builder()
                .code(200)
                .message("Cập nhật acccount thành công")
                .build();
    }

    @DeleteMapping(value = "/{accountId}")
    @ResponseBody
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("Xóa thành công");
    }


}
