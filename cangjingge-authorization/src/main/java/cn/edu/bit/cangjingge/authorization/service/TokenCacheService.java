package cn.edu.bit.cangjingge.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenCacheService {

    private StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setString(String key, String value) {
        set(key, value, null);
    }

    public void setString(String key, String value, Long timeOut) {
        set(key, value, timeOut);
    }

    private void set(String key, String value, Long timeOut) {
        if (value != null) {
            redisTemplate.opsForValue().set(key, value);

            //设置有效期
            if (timeOut != null) {
                redisTemplate.expire(key, timeOut, TimeUnit.MILLISECONDS);
            }
        }
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean contains(String key) {
        return redisTemplate.hasKey(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
