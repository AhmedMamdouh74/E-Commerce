package com.example.data.dataSource.cart

import com.example.data.dataSourceContract.CartDataSourceContact
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
abstract class Di {
    @Binds
    abstract fun bindCartDataSourceContract(
        cartDataSourceContactImpl: CartDataSourceContactImpl
    ): CartDataSourceContact
}