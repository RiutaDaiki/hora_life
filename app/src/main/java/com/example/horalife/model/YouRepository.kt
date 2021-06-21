package com.example.horalife.model

import android.util.Log
import com.example.horalife.dataClass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    suspend fun deleteUser(currentAccount: FirebaseUser?): Boolean {
        val result = deleteUserFun(currentAccount).getOrThrow()
        if(result) {
            return true
        }
        else {
            Log.e("Error", result.toString())
            return false
        }
    }

    private suspend fun deleteUserFun(currentAccount: FirebaseUser?): Result<Boolean> {
        return kotlin.runCatching {
            suspendCoroutine { continuation ->
                if (currentAccount != null) {

                    currentAccount.delete()
                        .addOnSuccessListener {
                            continuation.resume(true)
                        }
                        .addOnFailureListener {
                            continuation.resumeWithException(it)
                        }
                }
                else {
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
    }

}

