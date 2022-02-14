package com.badger.familyorgbe.controller.privacytest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/private")
class PrivateController {

    @GetMapping("health")
    fun healthCheck() = "Hello private"
}