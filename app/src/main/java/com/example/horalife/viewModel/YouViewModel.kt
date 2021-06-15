package com.example.horalife.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horalife.dataClass.User
import com.example.horalife.model.YouRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class YouViewModel : ViewModel() {
    object Repository {
        val repository = YouRepository()
    }

    fun deleteAccount(){
        viewModelScope.launch {
            val bool : Flow<Boolean> = Repository.repository.deleteUser()
            bool.collect {
                println(it)
            }
        }
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