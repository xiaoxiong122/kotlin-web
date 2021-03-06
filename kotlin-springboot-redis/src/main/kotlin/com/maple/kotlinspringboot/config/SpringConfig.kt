package com.maple.kotlinspringboot.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.convert.ConversionService
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.core.convert.support.DefaultConversionService
import org.springframework.web.method.support.HandlerMethodArgumentResolver


/**
 * TODO
 *
 * @author maple
 * @version V1.0
 * @since 2019-01-15 15:46
 */

@Configuration
class SpringConfig : WebMvcConfigurer {

    @Autowired
    lateinit var currentUserResolver: CurrentUserMethodArgumentResolver

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("forward:/hello")
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE)
    }
//
//    @Bean
//    fun conversionService(): ConversionService{
//        return DefaultConversionService()
//    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        resolvers.add(currentUserResolver)
    }
}