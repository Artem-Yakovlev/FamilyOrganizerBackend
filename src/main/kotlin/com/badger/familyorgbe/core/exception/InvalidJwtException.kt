package com.badger.familyorgbe.core.exception

import org.springframework.security.core.AuthenticationException

class InvalidJwtException(msg: String) : AuthenticationException(msg)