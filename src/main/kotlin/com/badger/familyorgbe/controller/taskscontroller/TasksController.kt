package com.badger.familyorgbe.controller.taskscontroller

import com.badger.familyorgbe.controller.taskscontroller.json.*
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.core.base.rest.BaseResponse
import com.badger.familyorgbe.core.base.rest.ResponseError
import com.badger.familyorgbe.models.entity.Measure
import com.badger.familyorgbe.models.entity.task.TaskStatus
import com.badger.familyorgbe.models.usual.task.Subtask
import com.badger.familyorgbe.models.usual.task.Task
import com.badger.familyorgbe.models.usual.task.TaskCategory
import com.badger.familyorgbe.models.usual.task.TaskProduct
import com.badger.familyorgbe.service.family.FamilyService
import com.badger.familyorgbe.service.family.actionIfHasAccess
import com.badger.familyorgbe.service.tasks.TasksService
import org.hibernate.annotations.Check
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import kotlin.random.Random


@RestController
@RequestMapping("/tasks")
class TasksController : BaseAuthedController() {
    @Autowired
    private lateinit var tasksService: TasksService

    @Autowired
    private lateinit var familyService: FamilyService

    @PostMapping("getAll")
    suspend fun getAll(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: GetAllJson.Form
    ): BaseResponse<GetAllJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.actionIfHasAccess(familyId = form.familyId, email = email) {
            BaseResponse(
                data = GetAllJson.Response(tasks = tasksService.getAllTasksByFamilyId(form.familyId))
            )
        } ?: BaseResponse(error = ResponseError.FAMILY_DOES_NOT_EXISTS, data = GetAllJson.Response(emptyList()))
    }

    @PostMapping("create")
    suspend fun create(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: CreateJson.Form
    ): BaseResponse<CreateJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.actionIfHasAccess(familyId = form.familyId, email = email) {
            tasksService.createFamilyTask(task = form.task, familyId = form.familyId)
            BaseResponse(data = CreateJson.Response())
        } ?: BaseResponse(error = ResponseError.FAMILY_DOES_NOT_EXISTS, data = CreateJson.Response())
    }

    @PostMapping("delete")
    suspend fun delete(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: DeleteJson.Form
    ): BaseResponse<DeleteJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.actionIfHasAccess(familyId = form.familyId, email = email) {
            tasksService.changeTaskStatus(familyId = form.familyId, taskId = form.taskId, status = TaskStatus.FAILED)
            BaseResponse(data = DeleteJson.Response())
        } ?: BaseResponse(error = ResponseError.FAMILY_DOES_NOT_EXISTS, data = DeleteJson.Response())
    }

    @PostMapping("modify")
    suspend fun modify(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: ModifyJson.Form
    ): BaseResponse<ModifyJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.actionIfHasAccess(familyId = form.familyId, email = email) {
            tasksService.modifyFamilyTask(
                familyId = form.familyId,
                task = form.task
            )
            tasksService.updateFamilyTasksStatus(
                familyId = form.familyId
            )
            BaseResponse(data = ModifyJson.Response())
        } ?: BaseResponse(error = ResponseError.FAMILY_DOES_NOT_EXISTS, data = ModifyJson.Response())
    }

    @PostMapping("checkSubtaskOrProduct")
    suspend fun checkSubtaskOrProduct(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: CheckSubtaskOrProductJson.Form
    ): BaseResponse<CheckSubtaskOrProductJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.actionIfHasAccess(familyId = form.familyId, email = email) {
            tasksService.checkSubtask(
                familyId = form.familyId,
                taskId = form.taskId,
                subtaskId = form.subtaskId,
                productId = form.productId
            )
            tasksService.updateFamilyTasksStatus(
                familyId = form.familyId
            )
            BaseResponse(data = CheckSubtaskOrProductJson.Response())
        } ?: BaseResponse(error = ResponseError.FAMILY_DOES_NOT_EXISTS, data = CheckSubtaskOrProductJson.Response())
    }

    @PostMapping("changeStatus")
    suspend fun changeStatus(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody form: ChangeStatusJson.Form
    ): BaseResponse<ChangeStatusJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.actionIfHasAccess(familyId = form.familyId, email = email) {
            tasksService.deleteFamilyTaskById(familyId = form.familyId, taskId = form.taskId)
            BaseResponse(data = ChangeStatusJson.Response())
        } ?: BaseResponse(error = ResponseError.FAMILY_DOES_NOT_EXISTS, data = ChangeStatusJson.Response())
    }
}



