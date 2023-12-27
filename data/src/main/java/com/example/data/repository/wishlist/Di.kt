package com.example.data.repository.wishlist

import com.example.domain.repositories.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindsWishlistRepository(wishlistRepositoryImpl: WishlistRepositoryImpl): WishlistRepository
}