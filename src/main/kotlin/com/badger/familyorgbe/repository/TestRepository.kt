package com.badger.familyorgbe.repository

import com.badger.familyorgbe.model.Message
import org.springframework.data.jpa.repository.JpaRepository

interface TestRepository : JpaRepository<Message, Int>