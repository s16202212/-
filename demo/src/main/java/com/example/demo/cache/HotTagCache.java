package com.example.demo.cache;

import com.example.demo.dto.HotTagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: lai
 * @DateTime: 2020/6/3 13:21
 */
public class HotTagCache {
    public static List<HotTagDTO> get() {
        List<HotTagDTO> hotTagDTOS = new ArrayList<>();
        HotTagDTO hotTagDTO = new HotTagDTO();
        hotTagDTO.setTags(Arrays.asList("java", "spring", "spring boot", "python", "javaScript"));
        hotTagDTOS.add(hotTagDTO);

        return hotTagDTOS;
    }

}
