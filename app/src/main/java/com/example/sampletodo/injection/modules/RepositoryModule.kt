package com.example.sampletodo.injection.modules

import com.example.sampletodo.repository.ITodoRepository
import com.example.sampletodo.repository.TodoRepository
import com.example.sampletodo.storage.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object RepositoryModule {

  @Provides
    @Singleton
    fun providesTodoRepository(todoDao: TodoDao): ITodoRepository =
      TodoRepository(todoDao)

 /*   @Provides
    @Singleton
    fun providesProductDetailsRepository(apiServices: ApiServices): ProductDetailsRepositoryI =
        ProductDetailsRepository(apiServices)*/
}