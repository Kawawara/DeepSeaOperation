package fr.theo.berton.deepseaoperation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application): AndroidViewModel(application) {

    val currentUsername = MutableLiveData("Username")

    fun setUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(username.isNotEmpty()) {
                currentUsername.postValue(username)
            }
        }
    }
}