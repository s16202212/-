package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/27 10:46
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    @Results(id="userMap",value={
            @Result(property = "userId", column = "user_id", jdbcType= JdbcType.INTEGER),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "gmtCreate", column = "gmt_create", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtModified", column = "gmt_modified", jdbcType= JdbcType.INTEGER),
    })
    List<User> findAll();

    @Select("select * from user where email=#{email} and password=#{password}")
    @ResultMap("userMap")
    User login(@Param("email") String email, @Param("password") String password);

    @Insert("insert into user (email,name,password) values (#{email},#{name},#{password})")
    void regist(User user);

    @Select("select * from user where user_id=#{userId}")
    User findById(@Param("userId") Long userId);

    @Update("update user set email = #{email}, name = #{name}, password = #{password}, gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl}, file_name = #{fileName} where user_id = #{userId}")
    void update(User user);

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    void delect(Long userId);

    @Select("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1")
    User findNear();
}
