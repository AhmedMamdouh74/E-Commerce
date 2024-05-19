package com.example.data.datasource.login

import com.example.data.datasourcecontract.LoginDataSourceContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindLoginDataSource(loginDataSourceImpl: LoginDataSourceImpl):LoginDataSourceContract
}