package com.example.nav_drawer.ui.photo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nav_drawer.R

class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = application.resources.getString(R.string.lorem) //"This is gallery Fragment"
    }
    val text: LiveData<String> = _text
}