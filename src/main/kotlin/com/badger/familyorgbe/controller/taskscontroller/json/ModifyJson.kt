package com.badger.familyorgbe.controller.taskscontroller.json

import com.badger.familyorgbe.models.usual.task.Task

class ModifyJson {

    data class Form(
        val familyId: Long,
        val task: Task
    )

    class Response
}