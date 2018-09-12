package tech.jaya.octo.model


import java.util.*

data class IssueEntity(
        val action: Action,
        val id: Int,
        val number: Int,
        val title: String,
        val createdAt: Date?,
        val updatedAt: Date?,
        val login:String
)