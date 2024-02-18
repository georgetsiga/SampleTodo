package com.example.sampletodo.injection.components

import com.example.sampletodo.injection.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent