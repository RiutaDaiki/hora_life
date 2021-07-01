package com.riuta.horalife.model

import android.util.Log
import com.riuta.horalife.dataClass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.riuta.horalife.dataClass.Setting
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class YouRepository {
    private val db = Firebase.firestore
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val users = "users"

    fun createUser(user: User) {
        db.collection(users).document(user.userId)
            .set(user)
            .addOnSuccessListener {
                createUserSetting(user.userId)
            }

    }

    fun createUserSetting(id: String){
//        db.collection(users).document(id)
//            .collection("setting")
//            .add()
    }

    fun checkExisting(userId: String, existing: (Boolean) -> Unit) {
        db.collection(users).document(userId)
            .get()
            .addOnSuccessListener {
                existing(true)
            }
            .addOnFailureListener {
                existing(false)
            }
    }

    suspend fun deleteUser(currentAccount: FirebaseUser): Boolean {
        return deleteUserFun(currentAccount).getOrNull() ?: false
    }

    private suspend fun deleteUserFun(currentAccount: FirebaseUser): Result<Boolean> {
        return kotlin.runCatching {
            suspendCoroutine { continuation ->

                currentUser.delete()
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        Log.e("Exception", it.toString())
                        continuation.resumeWithException(it)
                    }
            }
        }
    }

    suspend fun sendVerify(): Boolean {
        return sendVerifyFun().getOrNull() ?: false
    }

    private suspend fun sendVerifyFun(): Result<Boolean> {
        return kotlin.runCatching {
            suspendCoroutine { continuation ->
                currentUser.sendEmailVerification()
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        Log.e("error", it.toString())
                        continuation.resumeWithException(it)
                    }
            }
        }
    }

}

