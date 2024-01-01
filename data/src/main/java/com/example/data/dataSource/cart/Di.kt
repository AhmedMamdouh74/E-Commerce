package com.example.data.dataSource.cart

import com.example.data.dataSourceContract.CartDataSourceContact
import com.example.domain.repositories.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindCartDataSourceContract(cartDataSourceContactImpl: CartDataSourceContactImpl):CartDataSourceContact
}