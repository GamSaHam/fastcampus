package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.BoardServerException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.fastcampus.boardserver.utils.SHA256Util.encryptSHA256;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserProfileMapper userProfileMapper;

    @Autowired
    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public void register(UserDTO userProfile) {
        boolean duplicateIdResult = isDuplicateId(userProfile.getUserId());

        if(duplicateIdResult) {
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "중복된 아이디입니다");
        }

        userProfile.setCreateTime(LocalDateTime.now());
        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));

        try {
             userProfileMapper.register(userProfile);
        }catch (Exception e) {
            log.error("register 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        return memberInfo;
    }

    @Override
    public boolean isDuplicateId(String userId) {
        return userProfileMapper.idCheck(userId) > 0;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.selectUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String oldPassword, String newPassword) {
        String cryptoOldPassword = encryptSHA256(oldPassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoOldPassword);

        if(memberInfo == null) {
            log.error("updatePassword error {}", memberInfo);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "비밀번호 변경 메서드를 확인해 주세요");
        }

        memberInfo.setPassword(encryptSHA256(newPassword));
        try{
            userProfileMapper.updatePassword(memberInfo);
        }catch (Exception e) {
            log.error("updatePassword 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    public void deleteId(String id, String password) {
        String cryptoPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if(memberInfo == null) {
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "회원탈퇴 메서드를 확인해 주세요");
        }

        try{
            userProfileMapper.deleteUserProfile(id);
        }catch (Exception e) {
            log.error("deleteId 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
