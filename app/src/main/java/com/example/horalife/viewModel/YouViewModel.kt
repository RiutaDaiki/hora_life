package com.example.horalife.viewModel

import androidx.lifecycle.ViewModel
import com.example.horalife.entity.User
import com.example.horalife.model.YouRepository

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