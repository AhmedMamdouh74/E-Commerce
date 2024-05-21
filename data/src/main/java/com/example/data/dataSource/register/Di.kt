package com.example.data.dataSource.register

import com.example.data.datasourcecontract.RegisterDataSourceContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class Di {
    @Binds
   abstract fun bindRegisterDataSource(registerDataSourceImpl: RegisterDataSourceImpl): RegisterDataSourceContract
}