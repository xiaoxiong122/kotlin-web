package com.maple.kotlinspringboot.controller

import com.maple.kotlinspringboot.properties.UserProperties
import com.sun.xml.internal.fastinfoset.util.StringArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * TODO
 *
 * @author maple
 * @version V1.0
 * @since 2019-01-15 15:36
 */

@RestController
class HelloController {

    @Value("\${person.name}")
    private lateinit var username:String
    @Value("\${person.sex}")
    private lateinit var sex:String
//    @Value("\${person.children}")
//    private lateinit var children:Map<String,String>
    @Value("\${person.lists}")
    private lateinit var lists: List<String>

    @Autowired
    private lateinit var userProperties:UserProperties

    @GetMapping("/hello")
    fun hello(): String {
        return lists.toString()
    }

    @GetMapping("/test")
    fun test(): String {
        return userProperties.toString()
    }
}