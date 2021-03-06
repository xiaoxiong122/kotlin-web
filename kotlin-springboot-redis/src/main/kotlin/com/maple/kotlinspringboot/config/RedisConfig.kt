package com.maple.kotlinspringboot.config


import com.alibaba.fastjson.parser.ParserConfig
import com.maple.kotlinspringboot.utils.FastJsonRedisSerializer
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * redis 配置类
 *
 * @author maple
 * @version V1.0
 * @since 2019-01-02 09:53
 */
@Configuration
class RedisConfig {

    @Autowired
    lateinit var redisProperties: RedisProperties

    /**
     * 将redisTemplate格式化为string,any格式
     *
     * @param factory redis连接工厂
     * @return redisTemplate
     */
    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(factory)
//        val hessianRedisSerializer = HessianRedisSerializer(Any::class.java)
//        var javaRedisSerializer = JavaRedisSerializer<Any>()
        val fastJsonRedisSerializer = FastJsonRedisSerializer(Any::class.java)
        //配置白名单
        ParserConfig.getGlobalInstance().addAccept("com.maple.kotlinspringboot.entity")
        //或者直接关闭这个检测
//        ParserConfig.getGlobalInstance().isAutoTypeSupport = true
        val stringRedisSerializer = StringRedisSerializer()
        template.keySerializer = stringRedisSerializer
        template.hashKeySerializer = stringRedisSerializer
        template.valueSerializer = fastJsonRedisSerializer
        template.hashValueSerializer = fastJsonRedisSerializer
        template.afterPropertiesSet()
        return template
    }

    /**
     * 哨兵模式自动装配
     *
     * @return redis客户端
     */
    @Bean
    fun redisClient(): RedissonClient {
        val serverConfig = Config().apply {
            this.useSingleServer()
                    .setAddress("redis://${redisProperties.host}:${redisProperties.port}").timeout = (redisProperties.timeout.seconds * 1000).toInt()
        }
        return Redisson.create(serverConfig)
    }
}