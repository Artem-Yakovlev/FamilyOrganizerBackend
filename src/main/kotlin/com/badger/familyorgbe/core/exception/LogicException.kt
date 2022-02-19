package com.badger.familyorgbe.core.exception

import org.springframework.security.core.AuthenticationException

class LogicException(msg: String) : AuthenticationException(msg)