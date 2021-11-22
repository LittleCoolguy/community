package com.gao.community.schedule;

import com.gao.community.cache.HotTagCache;
import com.gao.community.entity.Question;
import com.gao.community.mapper.QuestionMapper;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 通过定时任务来获取最热标签
 * @author: XiaoGao
 * @time: 2021/11/7 19:14
 */
@Slf4j
@Component
public class HotTagTasks {
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    HotTagCache hotTagCache;
    /**
     *  定时任务
     *  通过每天一次的获取所有话题的标签，进行排序，找出最热门的话题
     */
//    @Scheduled(cron = "0 0 18 * * ?")
//    @Scheduled(cron = "0 */1 * * * ?")
    public void hotTagSchedule(){
//        System.out.println("执行标签更新任务");
        Map<String, Integer> map=new HashMap<>(16);
        List<Question> questionList = questionMapper.selectList(null);
        //为什么可以是null，这说明QueryMapper的作用仅仅是添加条件的吗？
        for (Question question:questionList){
            String tagString = question.getTag();
            String[] tags = tagString.split(",");
           for (String tag:tags){
               Integer prenum = map.get(tag);
               if (prenum!=null){
                   map.put(tag,prenum+5+question.getCommentCount());
               }
               else {
                   map.put(tag,5+question.getCommentCount());
               }
           }
        }
        hotTagCache.updateTags(map);
    }
    public void hotTagSchedule2(String tags){
        //Redis实现
        System.out.println("执行标签更新任务-Redis");
        String[] split = tags.split(",");
        for (String s:split){
            Double hotTags = redisTemplate.boundZSetOps("hotTags").score(s);
            if (hotTags==null){
                redisTemplate.boundZSetOps("hotTags").add(s,5);
            }
            else redisTemplate.boundZSetOps("hotTags").add(s,hotTags+5);
        }
        hotTagCache.updateTags2();
    }
}
