package org.example.authservice.mapper;

import org.example.authservice.entity.User
import UserResponse

fun User.toResponse() = UserResponse(
    id = this.id,
    username = this.username,
    email = this.email,
    role = this.role.name
)


