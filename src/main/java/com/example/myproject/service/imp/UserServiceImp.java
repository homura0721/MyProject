package com.example.myproject.service.imp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myproject.dao.UserDao;
import com.example.myproject.model.User;
import com.example.myproject.service.UserService;
import com.example.myproject.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImp extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    RedisUtils redisUtils;

    /**
     * 先从redis里读
     * 登录成功返回id
     * 返回-1表示登录失败
     *
     * @param user
     * @return
     */
    public int login(User user) {
        User u = (User) redisUtils.get(user.getUsername());
        if (u != null && user.getPassword().equals(u.getPassword())) {
            return u.getId();
        }
        u = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()), false);
        if (u != null) {
            redisUtils.set(u.getUsername(), u, 3, TimeUnit.DAYS);
            return u.getId();
        } else {
            return -1;
        }
    }

    /**
     * 返回-1表示用户已存在
     * 返回200表示注册成功
     *
     * @param user
     * @return
     */
    public int register(User user) {
        User u = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()), false);
        if (u == null) {
            this.save(user);
            return 200;
        } else {
            return -1;
        }
    }

}
