package com.riuta.horalife.dataClass

data class DiaryDetailContent(val diaryId: String,
                              val recordedDate: String,
                              val comment: String,
                              val pngFileName: String,
                              val videoFileName: String,
                              val videoPath: String)