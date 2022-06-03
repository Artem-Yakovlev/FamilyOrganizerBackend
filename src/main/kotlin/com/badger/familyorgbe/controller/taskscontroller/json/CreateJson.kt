package com.badger.familyorgbe.controller.taskscontroller.json

import com.badger.familyorgbe.models.usual.task.Task

class CreateJson {

    class Form

    data class Response(
        val task: Task?
    )
}