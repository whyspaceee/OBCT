package com.obcteam.obct.domain.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val token : String
)