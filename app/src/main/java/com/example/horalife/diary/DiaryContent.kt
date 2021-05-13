package com.example.horalife.diary

import org.apache.http.conn.ssl.StrictHostnameVerifier
import java.sql.Blob

data class DiaryContent(val recordedDate: String, val comment: String, val pngFileName: String) {


}