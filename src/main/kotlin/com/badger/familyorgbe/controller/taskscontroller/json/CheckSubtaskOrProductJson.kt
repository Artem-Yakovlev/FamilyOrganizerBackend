package com.badger.familyorgbe.controller.taskscontroller.json

class CheckSubtaskOrProductJson {

    data class Form(
        val familyId: Long,
        val taskId: Long,
        val subtaskId: Long?,
        val productId: Long?
    )

    class Response
}