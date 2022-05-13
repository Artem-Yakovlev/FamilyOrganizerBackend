package com.badger.familyorgbe.service.family

import com.badger.familyorgbe.repository.family.IFamilyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FamilyService {

    @Autowired
    private lateinit var repository: IFamilyRepository

}