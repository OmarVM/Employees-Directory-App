package com.example.employeesdirectoryapp.data.mapper

sealed class ResponseHandler<T>(
    val progress: Int? = 0,
    val data: T? = null,
    val message: String? = null,
    val codeError: Int? = null
) {
    class SUCCESS<T>(data:T): ResponseHandler<T>(data = data)
    class ERROR<T>(data: T? = null, message: String?, codeError: Int? = null): ResponseHandler<T>(data = data, message = message, codeError = codeError)
    class LOADING<T>(progress: Int?): ResponseHandler<T>(progress = progress)
}
