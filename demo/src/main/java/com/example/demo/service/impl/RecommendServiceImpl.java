package com.example.demo.service.impl;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.RecommendMapper;
import com.example.demo.model.Question;
import com.example.demo.model.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/6/5 17:53
 */
@Service
public class RecommendServiceImpl implements com.example.demo.service.RecommendService{

    @Autowired
    RecommendMapper recommendMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<Recommend> queryAll() {
        return recommendMapper.queryAll();
    }

    @Override
    public void updateLike(Long id, Long userId ,Integer likeCount) {
        Question question = questionMapper.getById(id);
        int m = likeCount.intValue();
        int n = question.getLikeCount().intValue();
        Recommend recommend = new Recommend();
        recommend.setUid(userId);
        String[] tags = StringUtils.split(question.getTag(),"，");
        for (int i = 0; i<tags.length; i++){
            if ("java".equals(tags[i])){
                recommend.setJava(1);
            }
            if ("spring".equals(tags[i])){
                recommend.setSpring(1);
            }
            if ("python".equals(tags[i])){
                recommend.setPython(1);
            }
            if ("php".equals(tags[i])){
                recommend.setPhp(1);
            }
            if ("css".equals(tags[i])){
                recommend.setCss(1);
            }
        }
        if(m == n+1){
            recommendMapper.updateAddLike(recommend);
        }else {
            recommendMapper.updateLike(recommend);
        }
    }

    @Override
    public void updateComment(Long parentId, Long userId) {
        Question question = questionMapper.getById(parentId);
        Recommend recommend = new Recommend();
        recommend.setUid(userId);
        String[] tags = StringUtils.split(question.getTag(),"，");
        for (int i = 0; i<tags.length; i++){
            if ("java".equals(tags[i])){
                recommend.setJava(3);
            }
            if ("spring".equals(tags[i])){
                recommend.setSpring(3);
            }
            if ("python".equals(tags[i])){
                recommend.setPython(3);
            }
            if ("php".equals(tags[i])){
                recommend.setPhp(3);
            }
            if ("css".equals(tags[i])){
                recommend.setCss(3);
            }
        }
        recommendMapper.updateAddLike(recommend);
    }
}
