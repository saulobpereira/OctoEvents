package tech.jaya.octo.repository

data class IssueEntity(
        val action: Action,
        val id: Int,
        val number: Int,
        val title: String,
        val createdAt: String?,
        val updatedAt: String?,
        val login:String
)