package com.badger.familyorgbe.controller.taskscontroller.json

import com.badger.familyorgbe.models.usual.task.Task

class GetAllJson {

    data class Form(
        val familyId: Long
    )

    data class Response(
        val tasks: List<Task>
    )
}