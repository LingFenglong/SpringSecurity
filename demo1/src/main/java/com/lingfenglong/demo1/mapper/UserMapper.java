package com.lingfenglong.demo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingfenglong.demo1.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
