package com.teamsparta.todo

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GeneralResponse(
    val status: Int,
    val message: String,
    var data:Any? = null
)