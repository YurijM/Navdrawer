package com.example.nav_drawer

import android.app.Application
import com.example.nav_drawer.ui.recyclerview.model.UsersService

class App: Application() {
    val usersService = UsersService()
}