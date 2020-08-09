package com.example.demo.mapper;

import com.example.demo.model.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/5/8 0:30
 */
@Mapper
public interface CommentMapper {

    @Select("select * from comment where id = #{id} ORDER BY gmt_create DESC")
    @Results(id="commentWithUserMap",value={
            @Result(property = "id", column = "id", jdbcType= JdbcType.INTEGER),
            @Result(property = "parentId", column = "parent_id", jdbcType= JdbcType.INTEGER),
            @Result(property = "type", column = "type", jdbcType= JdbcType.INTEGER),
            @Result(property = "commentator", column = "commentator", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtCreate", column = "gmt_create", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtModified", column = "gmt_modified", jdbcType= JdbcType.INTEGER),
            @Result(property = "likeCount", column = "like_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "content", column = "content"),
            @Result(property = "commentCount", column = "comment_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "user", column = "commentator",one = @One(select = "com.example.demo.mapper.UserMapper.findById"))
    })
    Comment getAllByIdWithUser(@Param("id")Long id);

    @Select("select * from comment ORDER BY gmt_create DESC")
    @Results(id="commentMap",value={
            @Result(property = "id", column = "id", jdbcType= JdbcType.INTEGER),
            @Result(property = "parentId", column = "parent_id", jdbcType= JdbcType.INTEGER),
            @Result(property = "type", column = "type", jdbcType= JdbcType.INTEGER),
            @Result(property = "commentator", column = "commentator", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtCreate", column = "gmt_create", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtModified", column = "gmt_modified", jdbcType= JdbcType.INTEGER),
            @Result(property = "likeCount", column = "like_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "content", column = "content"),
            @Result(property = "commentCount", column = "comment_count", jdbcType= JdbcType.INTEGER),
    })
    List<Comment> queryAll();

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content,comment_count) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content},#{commentCount})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id = #{id} ORDER BY gmt_create DESC")
    @Results(id="comment1WithUserMap",value={
            @Result(property = "id", column = "id", jdbcType= JdbcType.INTEGER),
            @Result(property = "parentId", column = "parent_id", jdbcType= JdbcType.INTEGER),
            @Result(property = "type", column = "type", jdbcType= JdbcType.INTEGER),
            @Result(property = "commentator", column = "commentator", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtCreate", column = "gmt_create", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtModified", column = "gmt_modified", jdbcType= JdbcType.INTEGER),
            @Result(property = "likeCount", column = "like_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "content", column = "content"),
            @Result(property = "commentCount", column = "comment_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "user", column = "commentator",one = @One(select = "com.example.demo.mapper.UserMapper.findById"))
    })
    List<Comment> getAllById(Long id);

    @Delete("DELETE FROM comment WHERE id = #{id}")
    void delete(Long id);
}
