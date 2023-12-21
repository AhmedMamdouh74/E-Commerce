package com.example.data.dataSourceContract


import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category

interface CategoryDataSourceContract {
suspend fun getCategories():ResultWrapper< List<Category?>?>
}