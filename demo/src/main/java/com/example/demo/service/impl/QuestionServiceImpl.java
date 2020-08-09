package com.example.demo.service.impl;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.RecommendMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/30 16:21
 */
@Service
public class QuestionServiceImpl implements com.example.demo.service.QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RecommendMapper recommendMapper;

    @Override
    public List<Question> queryAll(){
        return questionMapper.queryAll();
    }

    @Override
    public int create(Question question){
        return questionMapper.create(question);
    }

    @Override
    public Question getAllByIdWithUser(Long id){
        return questionMapper.getAllByIdWithUser(id);
    }

    @Override
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
            Question question1 = questionMapper.findNear();
            Recommend recommend = new Recommend();
            recommend.setUid(question1.getCreator());
            String[] tags = StringUtils.split(question1.getTag(),"，");
            for (int i = 0; i<tags.length; i++){
                if ("java".equals(tags[i])){
                    recommend.setJava(5);
                }
                if ("spring".equals(tags[i])){
                    recommend.setSpring(5);
                }
                if ("python".equals(tags[i])){
                    recommend.setPython(5);
                }
                if ("php".equals(tags[i])){
                    recommend.setPhp(5);
                }
                if ("css".equals(tags[i])){
                    recommend.setCss(5);
                }
            }
            recommendMapper.updateAddLike(recommend);
        }else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }

    @Override
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionMapper.incView(question);
    }

    @Override
    public List<Question> queryById(Long userId) {
        return questionMapper.queryById(userId);
    }

    @Override
    public void comcount(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setCommentCount(1);
        questionMapper.comcount(question);
    }

    @Override
    public List<Question> queryByTag(String tag) {
        return questionMapper.queryByTag(tag);
    }

    @Override
    public List<Question> getAllByQuestionRelation(Question question) {
        if ("".equals(question.getTag())){
            return null;
        }else {
            String tags = question.getTag().replace('，','|');
            Question question1 = new Question();
            question1.setId(question.getId());
            question1.setTag(tags);
            List<Question> questions = questionMapper.getAllByQuestionRelation(question1);
            return questions;
        }

    }

    @Override
    public void updateLikeCount(Long id, Integer likeCount) {
        Question question = new Question();
        question.setId(id);
        question.setLikeCount(likeCount);
        question.setGmtModified(question.getGmtCreate());
        questionMapper.updateLikeCount(question);
    }

    @Override
    public Question getById(Long id) {
        return questionMapper.getById(id);
    }

    @Override
    public List<Question> queryByView() {
        return questionMapper.queryByView();
    }

    @Override
    public List<Question> queryByTags() {
        List<Question> questionList = questionMapper.queryByTags();
        return questionList;
    }

    @Override
    public void delete(Long id) {
        questionMapper.delete(id);
    }

    @Override
    public List<Question> recommend(Long userId) {
        List<Question> questionList1 = questionMapper.getAll();
        List<Question> questionList2 = questionMapper.getAll();
        List<Question> questionList = new ArrayList<>();
        double[][] k = new double[200][200];
        for (Question a: questionList1){
            Integer m1 = a.getViewCount();
            Integer m2 = a.getLikeCount();
            Integer m3 = a.getCommentCount();
            for (Question b: questionList2){
                int ai = a.getId().intValue();
                int bi = b.getId().intValue();
                Integer n1 = b.getViewCount();
                Integer n2 = b.getLikeCount();
                Integer n3 = b.getCommentCount();
                if (a.getId().equals(b.getId())){
                    continue;
                }
                double o = m1*n1+m2*n2+m3*n3;
                double e = m1*m1+m2*m2+m3*m3;
                double f = n1*n1+n2*n2+n3*n3;
                double e1 = Math.sqrt(e);
                double f1 = Math.sqrt(f);
                double o1 = o/(e1*f1);
                k[ai][bi] = o1;
            }
            continue;
        }
        double max = 0;
        for (int i = 0; i < k.length-1; i++) {
            for (int j = 0; j < k.length - i - 1; j++) {
                if(i!=j){
                    if(max < k[i][j]){
                        max = k[i][j];
                    }
                }
            }
        }
        for (int i = 0; i < k.length-1; i++) {
            Long i1 = new Integer(i).longValue();
            for (int j = 0; j < k.length - i - 1; j++) {
                if(i!=j){
                    if(k[i][j] >= 0.997){
                        Long j1 = new Integer(j).longValue();
                        Question question1 = questionMapper.getById(i1);
                        Question question2 = questionMapper.getById(j1);
                        System.out.println(i1);
                        System.out.println(j1);
                        if (!question2.getCreator().equals(userId)){
                            System.out.println(userId);
                            System.out.println("---------------");
                            System.out.println(question2.getCreator());
                            questionList.add(question2);
                            System.out.println(question2);
                        }
                    }
                }
            }
        }
        return questionList;
    }
}
