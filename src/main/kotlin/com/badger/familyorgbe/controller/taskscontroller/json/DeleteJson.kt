package com.badger.familyorgbe.controller.taskscontroller.json

class DeleteJson {

    data class Form(
        val familyId: Long,
        val taskId: Long
    )

    class Response
}