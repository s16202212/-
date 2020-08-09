package com.example.demo.service;

import com.example.demo.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/4/30 16:17
 */
@Service
public interface QuestionService {

    List<Question> queryAll();

    int create(Question question);

    Question getAllByIdWithUser(Long id);

    void createOrUpdate(Question question);

    void incView(Long id);

    List<Question> queryById(Long userId);

    void comcount(Long id);

    List<Question> queryByTag(String tag);

    List<Question> getAllByQuestionRelation(Question question);

    void updateLikeCount(Long id,Integer likeCount);

    Question getById(Long id);

    List<Question> queryByView();

    List<Question> queryByTags();

    void delete(Long id);

    List<Question> recommend(Long userId);

    //    List<Question> queryRecommend(int userId);
}
