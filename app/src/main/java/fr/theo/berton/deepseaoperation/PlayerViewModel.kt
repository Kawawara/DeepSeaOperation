package fr.theo.berton.deepseaoperation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import fr.theo.berton.deepseaoperation.database.AppDatabase

class PlayerViewModel(application: Application): AndroidViewModel(application) {
    val db = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "deep-sea-database"
    ).build()
    val sharedPreferences = application.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    val username = MutableLiveData<String>(null)

    init{
        username.postValue(sharedPreferences.getString("USERNAME", "default"))
    }

}