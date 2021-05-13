package com.example.horalife.diary

import java.sql.Blob

data class DiaryContent(val recordedDate: String, val comment: String, val thumbnail: Blob) {


}