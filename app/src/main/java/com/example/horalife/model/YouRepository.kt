package com.example.horalife.model

import com.example.horalife.dataClass.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class YouRepository {
    private val db = Firebase.firestore

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
}