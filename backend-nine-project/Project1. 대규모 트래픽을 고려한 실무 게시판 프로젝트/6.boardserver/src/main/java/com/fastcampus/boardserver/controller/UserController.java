package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.request.UserDeleteIdRequest;
import com.fastcampus.boardserver.dto.request.UserLoginRequest;
import com.fastcampus.boardserver.dto.request.UserUpdatePasswordRequest;
import com.fastcampus.boardserver.dto.response.LoginResponse;
import com.fastcampus.boardserver.dto.response.UserInfoResponse;
import com.fastcampus.boardserver.service.UserService;
import com.fastcampus.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        if (userDTO.hasNullBeforeRegister(userDTO)) {
            return ResponseEntity.badRequest().body("회원가입 정보를 확인해 주세요.");
        }

        userService.register(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity;

        String userId = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();

        UserDTO userInfo = userService.login(userId, password);

        LoginResponse loginResponse;

        if (userInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            loginResponse = LoginResponse.success(userInfo);
            if (userInfo.getStatus() == UserDTO.Status.ADMIN) {
                SessionUtil.setLoginAdminId(session, userInfo.getUserId());
            } else {
                SessionUtil.setLoginMemberId(session, userInfo.getUserId());
            }
            responseEntity = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }

        return responseEntity;
    }

    @GetMapping("/myinfo")
    public UserInfoResponse memberInfo(HttpSession session) {
        String userId = SessionUtil.getLoginMemberId(session);

        if(userId == null)  {
            userId = SessionUtil.getLoginAdminID(session);
        }

        UserDTO memberInfo = userService.getUserInfo(userId);

        return  new UserInfoResponse(memberInfo);
    }

    @PutMapping("/logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("/password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<LoginResponse> updateUserPassword(String userId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
        ResponseEntity<LoginResponse> responseEntity = null;
        LoginResponse loginResponse;

        String oldPassword = userUpdatePasswordRequest.getOldPassword();
        String newPassword = userUpdatePasswordRequest.getNewPassword();

        try {
            userService.updatePassword(userId, oldPassword, newPassword);
            UserDTO userInfo = userService.login(userId, newPassword);
            loginResponse = LoginResponse.success(userInfo);
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            log.error("updateUserPassword error {}", e.getMessage());
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteIdRequest request, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity;

        String id = SessionUtil.getLoginMemberId(session);

        try {
            UserDTO userDTO = userService.login(id, request.getPassword());
            userService.deleteId(id, request.getPassword());
            LoginResponse loginResponse = LoginResponse.success(userDTO);
            responseEntity = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }catch (RuntimeException e) {
            log.error("deleteId error {}", e.getMessage());
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

}
