package com.example.horalife.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horalife.entity.User
import com.example.horalife.model.YouRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class YouViewModel : ViewModel() {
    object Repository {
        val repository = YouRepository()
    }

    private val user = Firebase.auth.currentUser
    private val _isDeleteUser = MutableSharedFlow<Boolean>()
    val isDeleteUser: Flow<Boolean> = _isDeleteUser
    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            if (user != null) {
                val result = Repository.repository.deleteUser()
                Log.d("VIEWMODEL", result.toString())
                _isDeleteUser.emit(result)
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

    private val _isSendVerifyMail = MutableSharedFlow<Boolean>()
    val isSendVerifyMail = _isSendVerifyMail

    fun sendVerify() {
        if (user != null && !user.isEmailVerified) {
            viewModelScope.launch(Dispatchers.IO) {
                val bool = Repository.repository.sendVerify()
                _isSendVerifyMail.emit(bool)
            }
        }
    }
}