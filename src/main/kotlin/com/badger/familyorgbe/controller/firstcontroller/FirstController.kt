package com.badger.familyorgbe.controller.firstcontroller

import com.badger.familyorgbe.repository.test.ITestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FirstController {

    @Autowired
    private lateinit var repository: ITestRepository

//    @GetMapping("/test")
//    fun test(model: Model): List<Message> {
//
//        return repository.findAll()
//    }

    @GetMapping("/test")
    fun test(model: Model): String {
        return "Hello world!"
    }

    @GetMapping("/testAuth")
    fun testAuth(model: Model): String {
        return "Hello auth!"
    }
}