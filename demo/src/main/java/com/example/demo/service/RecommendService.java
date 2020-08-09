package com.example.demo.service;

import com.example.demo.model.Recommend;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/6/5 17:53
 */
@Service
public interface RecommendService {

    List<Recommend> queryAll();

    void updateLike(Long id, Long userId, Integer likeCount);

    void updateComment(Long parentId, Long userId);
}
