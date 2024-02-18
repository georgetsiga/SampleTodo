package com.example.sampletodo.injection.modules

import android.content.Context
import androidx.room.Room
import com.example.sampletodo.MyApplication
import com.example.sampletodo.storage.db.TodoRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object AppModule {

    @Provides
    @Singleton
    fun providesContext(): Context = MyApplication.getContext()

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        TodoRoomDatabase::class.java,
        "todo_database"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: TodoRoomDatabase) = db.todoDao()
}