package com.badger.familyorgbe.repository.tasks

import com.badger.familyorgbe.models.entity.task.TaskProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IFamilyTaskProductsRepository : JpaRepository<TaskProductEntity, Long>