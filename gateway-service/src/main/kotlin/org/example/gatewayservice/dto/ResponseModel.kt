package org.example.gatewayservice.dto;

data class ResponseModel<T>(
    val statusCode: Int,
    val isSuccess: Boolean,
    val message: String,
    val data: T?
)
