package com.example.horalife.model

import com.example.horalife.dataClass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
//
    suspend fun deleteUser(): kotlinx.coroutines.flow.Flow<Boolean>{
//            suspendCoroutine { continuation ->
//                user.delete()
//                    .addOnCompleteListener { task ->
//                        continuation.resume(true)
//                    }
//                    .addOnFailureListener {
//                        continuation.resumeWithException(it)
//                    }
//            }
//
    }

}