package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.UserDTO;

public interface UserService {
    void register(UserDTO userProfile);
    UserDTO login(String id, String password);
    boolean isDuplicateId(String userId);
    UserDTO getUserInfo(String userId);
    void updatePassword(String id, String oldPassword, String newPassword);
    void deleteId(String id, String password);
}
