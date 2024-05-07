package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {
    UserDTO selectUserProfile(@Param("userId") String userId);
    int register(UserDTO userDTO);
    int deleteUserProfile(@Param("userId") String userId);
    UserDTO findByIdAndPassword(@Param("userId") String userId, @Param("password") String password);
    int idCheck(@Param("userId") String userId);
    int updatePassword(UserDTO user);
}
