package com.youkeda.app.service.impl;

import com.youkeda.app.model.Comment;
import com.youkeda.app.service.CommentService;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author 20891
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Long add(Comment comment) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String now = LocalDate.now().format(dateTimeFormatter);

        RAtomicLong atomicLong = redissonClient.getAtomicLong(now);

        atomicLong.expire(1, TimeUnit.DAYS);

        return redisTemplate.opsForList().leftPush("comment", now + getAutoIncrId());
    }

    /**
     * 获取自增长 id
     *
     * @return
     */
    private String getAutoIncrId() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String now = LocalDate.now().format(dateTimeFormatter);

        RAtomicLong atomicLong = redissonClient.getAtomicLong(now);

        atomicLong.expire(1, TimeUnit.DAYS);

        long number = atomicLong.incrementAndGet();

        return String.format("%08d", number);
    }

}
