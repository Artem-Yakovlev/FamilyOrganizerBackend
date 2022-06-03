package com.badger.familyorgbe.controller.taskscontroller

import com.badger.familyorgbe.controller.taskscontroller.json.*
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.core.base.rest.BaseResponse
import com.badger.familyorgbe.service.tasks.TasksService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import kotlin.random.Random


@RestController
@RequestMapping("/tasks")
class TasksController : BaseAuthedController() {
    @Autowired
    private lateinit var tasksService: TasksService

    @PostMapping("getAll")
    suspend fun getAll(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: GetAllJson.Form
    ): GetAllJson.Response {
        return GetAllJson.Response()
    }

    @PostMapping("create")
    suspend fun create(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: CreateJson.Form
    ): BaseResponse<CreateJson.Response> {
        tasksService.createFamilyTask(form.task)
        return BaseResponse(data = CreateJson.Response())
    }

    @PostMapping("delete")
    suspend fun delete(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: DeleteJson.Form
    ): DeleteJson.Response {
        return DeleteJson.Response()
    }

    @PostMapping("modify")
    suspend fun modify(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: ModifyJson.Form
    ): ModifyJson.Response {
        return ModifyJson.Response()
    }

    @PostMapping("changeStatus")
    suspend fun changeStatus(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: ChangeStatusJson.Form
    ): ChangeStatusJson.Response {
        return ChangeStatusJson.Response()
    }
}



