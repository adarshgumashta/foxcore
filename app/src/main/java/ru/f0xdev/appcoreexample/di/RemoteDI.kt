package ru.f0xdev.appcoreexample.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.f0xdev.appcoreexample.main.users.IUsersRemoteDataSource
import ru.f0xdev.appcoreexample.main.users.UsersRemoteDataSource

val remoteModule = module {
    single<IUsersRemoteDataSource> {
        val retrofit: Retrofit = get()
        UsersRemoteDataSource(retrofit.create(UsersRemoteDataSource.IUsersRestApi::class.java))
    }
}