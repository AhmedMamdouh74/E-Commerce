package com.example.data.dataSourceContract


import com.example.domain.model.Category

interface CategoryDataSource {
suspend fun getCategories():List<Category?>?
}