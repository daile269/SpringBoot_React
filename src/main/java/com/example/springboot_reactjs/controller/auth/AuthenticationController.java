package com.example.springboot_reactjs.controller.auth;

import com.example.springboot_reactjs.dto.AccountDTO;
import com.example.springboot_reactjs.dto.request.AuthenticationRequest;
import com.example.springboot_reactjs.dto.request.LogoutRequest;
import com.example.springboot_reactjs.dto.response.ApiResponse;
import com.example.springboot_reactjs.dto.response.AuthenticationResponse;
import com.example.springboot_reactjs.entity.Account;
import com.example.springboot_reactjs.service.IAccountService;
import com.example.springboot_reactjs.service.Impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping()
public class AuthenticationController {

    private final IAccountService accountService;

    private final JwtService jwtService;

    @PostMapping(value = "/register")
    @ResponseBody
    private ApiResponse register(@RequestBody Account account){
        accountService.checkUsernameExist(account.getUsername());
        accountService.saveAccount(account);
        return ApiResponse.builder().code(200).message("Đăng ký thành công").build();
    }

    @PostMapping(value = "/auth/login")
    @ResponseBody
    public AuthenticationResponse login(@RequestBody AuthenticationRequest auth){
        return accountService.verify(auth);
    }

//    @PostMapping(value = "/auth/logout")
//    public ResponseEntity<String> logout(@RequestBody LogoutRequest request){
//        return ResponseEntity.ok("Logout Success");
//    }

    @GetMapping(value = "/auth/myInfo")
    @ResponseBody
    private AccountDTO getInfo(){
        return accountService.getMyInfo();
    }
}
