package com.gao.community.cache;

import com.gao.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @description:  热门标签对象类
 * @author: XiaoGao
 * @time: 2021/11/7 13:18
 */
@Data
@Component
public class HotTagCache {
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        System.out.println("标签更新1");
        int max=10;
        PriorityQueue<HotTagDTO> priorityQueue=new PriorityQueue<>();
        //用HotTagDTO做对象的原因是优先队列需要参数来进行排序
        tags.forEach((name,priority)->{
            HotTagDTO hotTagDTO=new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size()<max){
                priorityQueue.add(hotTagDTO);
            }
            else {
                HotTagDTO peek = priorityQueue.peek();
                if (peek.compareTo(hotTagDTO)>0){
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });
        //前10筛选
        HotTagDTO poll = priorityQueue.poll();
        List<String> sortedTags=new ArrayList<>();
        while (poll!=null){
            sortedTags.add(poll.getName());
            poll=priorityQueue.poll();
        }
        hots=sortedTags;
    }
    public void updateTags2() {
        System.out.println("标签更新2");
        Set<ZSetOperations.TypedTuple<Object>> hotTags = redisTemplate.boundZSetOps("hotTags").reverseRangeWithScores(0, -1);
        List<String> sortedTags=new ArrayList<>();
        Integer k=0;
        for (ZSetOperations.TypedTuple<Object> hotTag:hotTags){
            System.out.println(hotTag.getValue()+"  "+hotTag.getScore());
            sortedTags.add((String) hotTag.getValue());
            k++;
            if (k>10)break;
        }
        hots=sortedTags;
        System.out.println(sortedTags.size()+" "+hots.size());
    }
}
