package com.badger.familyorgbe.controller.testcontroller

import com.badger.familyorgbe.controller.familycontroller.json.GetFamilyJson
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.core.base.rest.BaseResponse
import com.badger.familyorgbe.models.entity.ProductEntity
import com.badger.familyorgbe.models.entity.UserStatus
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.family.IFamilyRepository
import com.badger.familyorgbe.service.family.FamilyService
import com.badger.familyorgbe.service.users.IOnlineStorage
import com.badger.familyorgbe.service.users.UserService
import com.badger.familyorgbe.utils.converters.convertToEmailList
import com.badger.familyorgbe.utils.converters.convertToEmailString
import kotlinx.coroutines.GlobalScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController : BaseAuthedController() {

    @Autowired
    private lateinit var familyRepository: IFamilyRepository

    @Autowired
    private lateinit var familyService: FamilyService

    @Autowired
    private lateinit var onlineStorage: IOnlineStorage

    @Autowired
    private lateinit var userService: UserService

//    @PostMapping("1")
//    suspend fun getFamily(
//    ) {
//        val users = listOf(
//            User(name = "Gleb", email = "gleb_you@gmail.com", status = UserStatus.UNDEFINED),
//            User(name = "Maria", email = "maria_you@gmail.com", status = UserStatus.UNDEFINED),
//            User(name = "Zhmil", email = "zhmil_you@gmail.com", status = UserStatus.UNDEFINED),
//        )
////        users.forEach(userService::saveUser)
//
//        val family = familyRepository.getFamilyById(1) ?: throw IllegalAccessError()
//        val newMembers = convertToEmailList(family.members) + users.map(User::email)
//        val newFamily = family.copy(members = convertToEmailString(newMembers))
//
//        familyRepository.save(newFamily)
//    }

    @PostMapping("1")
    suspend fun getFamily(
    ): Pair<Int, Int> {
        return 1 to 2
    }
}