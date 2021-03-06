package com.riuta.horalife.dataClass

import java.sql.Timestamp

data class DiaryContent(val recordedDate: String,
                        val comment: String,
                        val pngFileName: String,
                        val timestamp: Timestamp,
                        val videoFileName: String,
                        val videoPath: String)