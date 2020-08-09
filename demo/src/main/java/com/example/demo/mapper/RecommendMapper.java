package com.example.demo.mapper;

import com.example.demo.model.Recommend;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/6/5 18:01
 */
@Mapper
public interface RecommendMapper {
    @Select("select * from recommend")
    @Results(id="recommendMap",value={
            @Result(property = "uid", column = "uid", jdbcType= JdbcType.INTEGER),
            @Result(property = "java", column = "java", jdbcType= JdbcType.INTEGER),
            @Result(property = "spring", column = "spring", jdbcType= JdbcType.INTEGER),
            @Result(property = "python", column = "python", jdbcType= JdbcType.INTEGER),
            @Result(property = "php", column = "php", jdbcType= JdbcType.INTEGER),
            @Result(property = "css", column = "css", jdbcType= JdbcType.INTEGER),
    })
    List<Recommend> queryAll();

    @Insert("insert into recommend (uid,java,spring,python,php,css) values (#{uid},#{java},#{spring},#{python},#{php},#{css})")
    void insert(Recommend recommend);

    @Update("update recommend set java = java + #{java}, spring = spring + #{spring}, python = python + #{python}, php = php + #{php}, css = css + #{css} where uid = #{uid}")
    void updateAddLike(Recommend recommend);

    @Update("update recommend set java = java - #{java}, spring = spring - #{spring}, python = python - #{python}, php = php - #{php}, css = css - #{css} where uid = #{uid}")
    void updateLike(Recommend recommend);
}
