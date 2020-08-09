package com.example.demo.mapper;

import com.example.demo.model.Notification;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/5/16 11:39
 */
@Mapper
public interface NotificationMapper {
    @Select("select * from notification ORDER BY gmt_create DESC")
    @Results(id="notificationMap",value={
            @Result(property = "id", column = "id", jdbcType= JdbcType.INTEGER),
            @Result(property = "notifier", column = "notifier", jdbcType= JdbcType.INTEGER),
            @Result(property = "receiver", column = "receiver", jdbcType= JdbcType.INTEGER),
            @Result(property = "outerid", column = "outerid", jdbcType= JdbcType.INTEGER),
            @Result(property = "type", column = "type", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtCreate", column = "gmt_create", jdbcType= JdbcType.INTEGER),
            @Result(property = "status", column = "status", jdbcType= JdbcType.INTEGER),
            @Result(property = "notifierName", column = "notifier_name"),
            @Result(property = "outerTitle", column = "outer_title"),
    })
    List<Notification> queryAll();

    @Insert("insert into notification (notifier,receiver,outerid,type,gmt_create,status,notifier_name,outer_title) values (#{notifier},#{receiver},#{outerid},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    int insert(Notification notification);

    @Select("select * from notification where receiver = #{userId} ORDER BY gmt_create DESC")
    List<Notification> getByUserId(Long userId);

    @Delete("DELETE FROM notification WHERE receiver = #{userId} and notifier = #{creator} and type = #{type}")
    int delectByThree(Long userId, Long creator, int type);

    @Select("SELECT COUNT(receiver) FROM notification WHERE receiver = #{userId}")
    int getNums(Long userId);
}
