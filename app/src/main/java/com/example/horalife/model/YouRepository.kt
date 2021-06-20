package com.example.horalife.model

import android.util.Log
import com.example.horalife.dataClass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener {
                existing(true)
            }
            .addOnFailureListener {
                existing(false)
            }
    }

    suspend fun deleteUser(): Boolean {
        val result = deleteUserFun().getOrThrow()
        if(result) return true
        else {
            Log.e("Error", result.toString())
            return false
        }
    }

    private suspend fun deleteUserFun(): Result<Boolean> {
        return kotlin.runCatching {
            suspendCoroutine { continuation ->
                user.delete()
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        Log.e("えくせぷション", it.toString())
                        continuation.resumeWithException(it)
                    }
            }
        }
    }

//    fun deleteUser(callBack: (Boolean) -> Unit) {
//        user.delete()
//            .addOnSuccessListener {
//                callBack(true)
//            }
//            .addOnFailureListener {
//                callBack(false)
//            }
//    }

}

