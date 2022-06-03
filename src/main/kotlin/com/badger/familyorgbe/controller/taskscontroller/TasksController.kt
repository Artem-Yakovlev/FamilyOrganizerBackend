package com.badger.familyorgbe.controller.taskscontroller

import com.badger.familyorgbe.controller.taskscontroller.json.*
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.models.entity.Measure
import com.badger.familyorgbe.models.entity.task.TaskCategoryEntity
import com.badger.familyorgbe.models.entity.task.TaskEntity
import com.badger.familyorgbe.models.entity.task.TaskProduct
import com.badger.familyorgbe.models.entity.task.TaskStatus
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
    ): CreateJson.Response {
        val task = TaskEntity(
            id = Random.nextLong(),
            category = TaskCategoryEntity.createOneShot(),
            status = TaskStatus.ACTIVE,
            title = "Title",
            description = "Description",
            notifications = "",
            products = listOf(
                TaskProduct(
                    checked = false,
                    title = "Product1",
                    amount = .0,
                    measure = Measure.KILOGRAM
                ),
                TaskProduct(
                    checked = true,
                    title = "Product2",
                    amount = .5,
                    measure = Measure.KILOGRAM
                )
            ),
            subtasks = emptyList()
        )
        tasksService.createFamilyTask(task)
        return CreateJson.Response()
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



