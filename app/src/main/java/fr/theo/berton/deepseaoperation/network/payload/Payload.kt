package fr.theo.berton.deepseaoperation.network.payload

import com.google.gson.GsonBuilder

abstract  class Payload {
    fun jsonEncodeToString(T:Any): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(T).toString()
    }
}