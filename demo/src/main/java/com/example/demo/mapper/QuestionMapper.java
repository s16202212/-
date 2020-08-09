package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/30 16:17
 */
@Mapper
public interface QuestionMapper {

    @Select("select * from question where id = #{id} ORDER BY gmt_create DESC")
    @Results(id="questionWithUserMap",value={
            @Result(property = "id", column = "id", jdbcType= JdbcType.INTEGER),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "gmtCreate", column = "gmt_create", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtModified", column = "gmt_modified", jdbcType= JdbcType.INTEGER),
            @Result(property = "creator", column = "creator", jdbcType= JdbcType.INTEGER),
            @Result(property = "commentCount", column = "comment_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "viewCount", column = "view_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "likeCount", column = "like_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "tag", column = "tag"),
            @Result(property = "fileUrl", column = "file_url"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "user", column = "creator",one = @One(select = "com.example.demo.mapper.UserMapper.findById"))
    })
    Question getAllByIdWithUser(@Param("id")Long id);

    @Select("select * from question ORDER BY gmt_create DESC")
    @Results(id="questionMap",value={
            @Result(property = "id", column = "id", jdbcType= JdbcType.INTEGER),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "gmtCreate", column = "gmt_create", jdbcType= JdbcType.INTEGER),
            @Result(property = "gmtModified", column = "gmt_modified", jdbcType= JdbcType.INTEGER),
            @Result(property = "creator", column = "creator", jdbcType= JdbcType.INTEGER),
            @Result(property = "commentCount", column = "comment_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "viewCount", column = "view_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "likeCount", column = "like_count", jdbcType= JdbcType.INTEGER),
            @Result(property = "tag", column = "tag"),
            @Result(property = "fileUrl", column = "file_url"),
            @Result(property = "fileName", column = "file_name"),
    })
    List<Question> queryAll();

    @Select("select * from question")
    List<Question> getAll();

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    int create(Question question);

    @Select("select * from question where id = #{id} ORDER BY gmt_create DESC")
    Question getById(@Param("id") Long id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag}, file_url = #{fileUrl}, file_name = #{fileName} where id = #{id}")
    void update(Question question);

    @Update("update question set view_count = view_count + #{viewCount} where id = #{id}")
    void incView(Question question);

    @Select("select * from question where creator = #{userId} ORDER BY gmt_create DESC")
    List<Question> queryById(Long userId);

    @Update("update question set comment_count = comment_count + #{commentCount} where id = #{id}")
    void comcount(Question question);

    @Select("select * from question where tag like CONCAT('%',#{tag},'%') ORDER BY gmt_create DESC")
    List<Question> queryByTag(String tag);

    @Select("select * from question where id != #{id} and tag REGEXP #{tag} ORDER BY gmt_create DESC")
    List<Question> getAllByQuestionRelation(Question question);

    @Update("update question set like_count = #{likeCount} where id = #{id}")
    void updateLikeCount(Question question);

    @Select("select id,title from question ORDER BY view_count DESC limit 5")
    List<Question> queryByView();

    @Select("select id,tag from question ORDER BY view_count DESC")
    List<Question> queryByTags();

    @Delete("DELETE FROM question WHERE id = #{id}")
    void delete(Long id);

    @Select("SELECT creator,tag FROM question ORDER BY id DESC LIMIT 1")
    Question findNear();

}
