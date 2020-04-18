package com.example.springsecuritydb.services;

import com.example.springsecuritydb.entity.User;
import com.example.springsecuritydb.mappers.UserMapper;
//import com.example.springsecuritydb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
//    @Autowired
//    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findUserByUsername(username);
        User user = userMapper.loadUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("账号不存在!");
        }
        System.out.println("------------"+user.toString()+"-------------------");

        user.setRoles(userMapper.getUserRolesByUid(user.getId()));
        System.out.println("------------"+user.toString()+"-------------------");
        System.out.println("------------"+user.isCredentialsNonExpired()+"-------------------");

        return user;
    }

}
