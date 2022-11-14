package com.example.employeesdirectoryapp.data.mapper

sealed class ResponseHandler<T>(
    val data: T? = null,
    val message: String? = null,
    val codeError: Int? = null
) {
    class SUCCESS<T>(data:T): ResponseHandler<T>(data)
    class ERROR<T>(data: T? = null, message: String?, codeError: Int? = null): ResponseHandler<T>(data, message, codeError)
    class LOADING<T>: ResponseHandler<T>()
}
