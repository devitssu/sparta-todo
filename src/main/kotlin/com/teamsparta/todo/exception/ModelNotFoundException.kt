package com.teamsparta.todo.exception

class ModelNotFoundException(val modelName: String, val id: Long): RuntimeException(
    "Model $modelName not found with given id $id"
) {
}