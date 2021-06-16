package com.example.horalife.model

import android.util.Log
import com.example.horalife.dataClass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class YouRepository {
    private val db = Firebase.firestore
    private val user = FirebaseAuth.getInstance().currentUser

    fun createUser(user: User) {
        db.collection("users").document(user.userId)
            .set(user)
    }

    fun checkExisting(userId: String, existing: (Boolean) -> Unit) {
        //userId名で検索して既に登録済みのユーザだったらtrue
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener {
                existing(true)
            }
            .addOnFailureListener {
                existing(false)
            }
    }

    suspend fun deleteUserFunction(): kotlinx.coroutines.flow.Flow<Boolean> = flow<Boolean> {
        Log.d("", "ログログ")
//        if (deleteUserFun().getOrNull() == true) this.emit(true)
        deleteUserFun()
            .onSuccess {
                this.emit(true)
                Log.d("s", "サクセス")
            }
            .onFailure {
                this.emit(false)
            }

    }

    suspend fun deleteUser(): Boolean{
        return deleteUserFun().getOrNull() ?: false
    }

     private suspend fun deleteUserFun(): Result<Boolean> {
        return kotlin.runCatching {
            suspendCoroutine { continuation ->
                user.delete()
                    .addOnSuccessListener {
                        println("サクセス")
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        println("フェイル")
                        continuation.resumeWithException(it)
                        Log.d("onFailure", it.toString())
                    }
            }
        }
    }

}

