package com.example.horalife.diary

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.horalife.diary_detail.DiaryDetailContent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.sql.Timestamp
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DiaryRepository {
    private val db = Firebase.firestore
    private val storageRef = Firebase.storage.reference
    private val diaries = "diaries"
    private val users = "users"
    private val alreadyLoginUser = Firebase.auth.currentUser

    fun createEntriesInfo(user: FirebaseUser?, thum: Bitmap, content: DiaryContent, localVideo: Uri) {
        val baos = ByteArrayOutputStream()
        thum.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val path = UUID.randomUUID().toString() + ".png"
        val uploadImageRef = storageRef.child("horanikki-thumbnail/$path")
        uploadImageRef.putBytes(data)
        val videoUri = content.videoPath.toUri()
        val uploadVideoRef = storageRef.child("horanikki-video/${localVideo.lastPathSegment}")
        uploadVideoRef.putFile(localVideo)
        val contents = DiaryContent(
                content.recordedDate,
                content.comment,
                path,
                Timestamp(System.currentTimeMillis()),
                localVideo.lastPathSegment!!,
                content.videoPath
                )
        if (user == null) {
            db.collection(users)
                    .document(alreadyLoginUser.uid)
                    .collection(diaries)
                    .add(contents)
        } else {
            db.collection(users)
                    .document(user.uid)
                    .collection(diaries)
                    .add(contents)
        }
    }

    suspend fun readDiaryInfo(user: FirebaseUser?): Result<List<DiaryDetailContent>> {
        val result: Result<List<DiaryDetailContent>> = runCatching {
            suspendCoroutine { continuation ->
                if (user == null) {


                    db.collection(users)
                            .document(alreadyLoginUser.uid)
                            .collection(diaries)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .get()
                            .addOnSuccessListener { result ->

                                val storingList = mutableListOf<DiaryDetailContent>()

                                for (document in result) {
                                    val d = document.data
                                    val content = DiaryDetailContent(
                                            document.id,
                                            d["recordedDate"].toString(),
                                            d["comment"].toString(),
                                            d["pngFileName"].toString(),
                                            d["videoFileName"].toString(),
                                            d["videoPath"].toString())
                                    storingList.add(content)

                                }
                                continuation.resume(storingList)
                            }
                            .addOnFailureListener {
                                continuation.resumeWithException(it)
                            }
                } else {
                    db.collection(users)
                            .document(user.uid)
                            .collection(diaries)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .get()
                            .addOnSuccessListener { result ->

                                val storingList = mutableListOf<DiaryDetailContent>()

                                for (document in result) {
                                    val d = document.data
                                    Log.d("id", document.id)
                                    val content = DiaryDetailContent(
                                            document.id,
                                            d["recordedDate"].toString(),
                                            d["comment"].toString(),
                                            d["pngFileName"].toString(),
                                            d["videoFileName"].toString(),
                                            d["videoPath"].toString())
                                    storingList.add(content)
                                }
                                continuation.resume(storingList)
                            }
                            .addOnFailureListener {
                                continuation.resumeWithException(it)
                            }
                }
            }

        }
        return result
    }

    fun deleteDiary(user: FirebaseUser?, diary: DiaryDetailContent) {
        if (user == null) {
            println(diary.diaryId)
            println(alreadyLoginUser.uid)
            db.collection(users)
                    .document(alreadyLoginUser.uid)
                    .collection(diaries)
                    .document(diary.diaryId)
                    .delete()
        } else {
            db.collection(users).document(user.uid)
                    .collection(diaries)
                    .document(diary.diaryId)
                    .delete()
        }
        storageRef.child("horanikki-thumbnail/${diary.videoFileName}")
                .delete()

        storageRef.child("horanikki-thumbnail/${diary.pngFileName}")
                .delete()
    }

    suspend fun readVideoUri(videoFileName: String): Result<Uri> {
        return kotlin.runCatching {
            suspendCoroutine { continuation ->
                storageRef.child("horanikki-video/$videoFileName").downloadUrl
                        .addOnSuccessListener {
                            continuation.resume(it)
                        }
                        .addOnFailureListener {
                            continuation.resumeWithException(it)
                        }

            }
        }
    }

    suspend fun getByteArray(pngFileName: String): Result<ByteArray> {
        return kotlin.runCatching {
            suspendCoroutine { continuation ->
                val storageRef = Firebase.storage.reference
                val thumbnailRef =
                        storageRef.child("horanikki-thumbnail/$pngFileName")
                val ONE_MEGABYTE: Long = 1024 * 1024
                thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                    continuation.resume(it)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            }
        }
    }

}