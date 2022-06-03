package com.badger.familyorgbe.repository.tasks

import com.badger.familyorgbe.models.entity.task.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IFamilyTaskRepository : JpaRepository<TaskEntity, Long>