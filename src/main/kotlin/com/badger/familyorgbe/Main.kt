package com.badger.familyorgbe

import com.badger.familyorgbe.model.Message
import com.badger.familyorgbe.repository.TestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class MainApplication

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}

@RestController
class FirstController {

    @Autowired
    lateinit var repository: TestRepository

    @GetMapping("/test")
    fun test(model: Model): List<Message> {

        return repository.findAll()
    }
}