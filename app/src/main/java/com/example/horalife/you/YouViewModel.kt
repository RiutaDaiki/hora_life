package com.example.horalife.you

import androidx.lifecycle.ViewModel

class YouViewModel : ViewModel() {

    object Repository {
        val repository = YouRepository()
    }

    fun callCreateUser(email: String, id: String, name: String) {
        val newUser = User(email, id, name)
        Repository.repository.createUser(newUser)
    }

    fun callExisting(userId: String): Boolean {
        var result = true
        Repository.repository.checkExisting(userId) {
            result = it
        }
        return result
    }
}