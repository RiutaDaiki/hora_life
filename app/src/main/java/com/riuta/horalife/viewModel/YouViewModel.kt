package com.riuta.horalife.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riuta.horalife.dataClass.User
import com.riuta.horalife.model.YouRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

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
                val result = Repository.repository.deleteUser(user)
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

    val birthDay = MutableLiveData<String>()

    val userBirthDay = MutableLiveData<LocalDate>()
    fun updateUserAge(birthDay: LocalDate) {
            userBirthDay.value = birthDay
    }

    val isDarkTheme = MutableLiveData<Boolean>()

    val statusText = MutableLiveData<String>()

    val statusTextColor = MutableLiveData<Int>()

    val displayName = MutableLiveData<String>()

}