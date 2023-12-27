package com.example.data.dataSource.wishlist

import com.example.data.dataSourceContract.WishListDataSourceContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindsWishlistDataSourceContract(
        wishlistDataSourceContactImpl: WishlistDataSourceContactImpl
    ): WishListDataSourceContract

}