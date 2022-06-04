package com.badger.familyorgbe.repository.tasks

import com.badger.familyorgbe.models.entity.task.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface IFamilyTaskRepository : JpaRepository<TaskEntity, Long> {

    @Query(value = "select task from TaskEntity task where task.familyId = :familyId")
    fun getAllTasksForFamily(
        @Param(value = "familyId") familyId: Long
    ): List<TaskEntity>

}