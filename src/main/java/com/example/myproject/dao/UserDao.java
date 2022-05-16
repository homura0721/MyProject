package com.example.myproject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myproject.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
