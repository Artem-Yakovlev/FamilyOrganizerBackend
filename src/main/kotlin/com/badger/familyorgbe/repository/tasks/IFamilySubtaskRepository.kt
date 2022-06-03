package com.badger.familyorgbe.repository.tasks

import com.badger.familyorgbe.models.entity.task.SubtaskEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IFamilySubtaskRepository : JpaRepository<SubtaskEntity, Long>