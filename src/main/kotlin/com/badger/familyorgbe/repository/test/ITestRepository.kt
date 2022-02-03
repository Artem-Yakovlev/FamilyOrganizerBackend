package com.badger.familyorgbe.repository.test

import com.badger.familyorgbe.models.entity.Message
import org.springframework.data.jpa.repository.JpaRepository

interface ITestRepository : JpaRepository<Message, Int>