package com.badger.familyorgbe.controller.taskscontroller.json

import com.badger.familyorgbe.models.entity.task.TaskStatus

class ChangeStatusJson {

    data class Form(
        val familyId: Long,
        val taskId: Long,
        val status: TaskStatus
    )

    class Response
}