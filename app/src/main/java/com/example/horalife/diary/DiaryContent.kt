package com.example.horalife.diary

import java.sql.Timestamp

data class DiaryContent(val diaryId: String, val recordedDate: String, val comment: String, val pngFileName: String, val timestamp: Timestamp, val videoFileName: String)