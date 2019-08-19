package ru.f0xdev.appcoreexample.presentation.auth

import com.google.gson.Gson
import finance.robo.android.accountservice.models.AuthException
import retrofit2.HttpException
import ru.f0xdev.appcoreexample.presentation.BaseErrorMapper
import ru.f0xdev.appcoreexample.presentation.Error
import ru.f0xdev.f0xcore.presentation.errors.ErrorConsts.VALIDATION_ERROR
import ru.f0xdev.f0xcore.presentation.errors.IError
import java.io.IOException


class AuthErrorMapper(gson: Gson) : BaseErrorMapper(gson) {
    override fun mapThrowableToError(throwable: Throwable): IError {
        val error: IError = when (throwable) {
            is HttpException -> {
                mapHttpExceptionToError(throwable)
            }

            is IOException -> {
                createNetworkError(throwable)
            }

            is AuthException -> {
                when (throwable.type) {
                    AuthException.Type.WRONG_CREDENTIALS -> {
                        Error(
                            VALIDATION_ERROR,
                            mapOf(
                                "email" to listOf("Invalid email or password"),
                                "password" to listOf("Invalid email or password")
                            ),
                            throwable
                        )
                    }

                    AuthException.Type.UNKNOWN_ERROR -> {
                        createUnknownError(throwable)
                    }
                    AuthException.Type.NETWORK -> {
                        createNetworkError(throwable)
                    }
                    else -> {
                        createNetworkError(throwable)
                    }
                }
            }
            else -> createUnknownError(throwable)
        }
        return error.apply { rawException = throwable }
    }
}