package com.example.exampgr208

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val thumbnail_link: String
)
