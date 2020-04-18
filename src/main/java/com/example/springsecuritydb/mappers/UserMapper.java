package com.example.springsecuritydb.mappers;

import com.example.springsecuritydb.entity.Role;
import com.example.springsecuritydb.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    User loadUserByUsername(String username);
    List<Role> getUserRolesByUid(Integer id);
}
