package com.badger.familyorgbe.controller.taskscontroller

import com.badger.familyorgbe.controller.taskscontroller.json.*
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.core.base.rest.BaseResponse
import com.badger.familyorgbe.models.entity.Measure
import com.badger.familyorgbe.models.entity.task.TaskStatus
import com.badger.familyorgbe.models.usual.task.Subtask
import com.badger.familyorgbe.models.usual.task.Task
import com.badger.familyorgbe.models.usual.task.TaskCategory
import com.badger.familyorgbe.models.usual.task.TaskProduct
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

        val task = Task(
            id = 324,
            category = TaskCategory.OneShot,
            status = TaskStatus.ACTIVE,
            title = "Заголовок",
            description = "Описание",
            notifications = listOf("Artem", "Sister"),
            products = listOf(
                TaskProduct(id = 1, checked = true, title = "Product 1", amount = null, measure = null),
                TaskProduct(id = 2, checked = false, title = "Product 2", amount = 0.5, measure = Measure.LITER),
            ),
            subtasks = listOf(
                Subtask(id = 1, text = "Task 1", checked = false),
                Subtask(id = 2, text = "Task 2", checked = true)
            )
        )

        tasksService.createFamilyTask(task)

        return BaseResponse(data = CreateJson.Response(tasksService.getById(1)))
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



