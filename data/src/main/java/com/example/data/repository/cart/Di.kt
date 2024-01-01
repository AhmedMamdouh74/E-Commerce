package com.example.data.repository.cart

import com.example.domain.repositories.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindsCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository
}