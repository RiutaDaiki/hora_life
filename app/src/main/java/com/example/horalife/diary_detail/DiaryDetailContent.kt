package com.example.horalife.diary_detail

import java.sql.Timestamp

data class DiaryDetailContent(val diaryId: String,
                              val recordedDate: String,
                              val comment: String,
                              val pngFileName: String,
                              val videoFileName: String,
                              val videoInMediaStore: String,
                              val videoPath: String)