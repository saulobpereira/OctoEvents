package tech.jaya.octo

data class IssueRequest(
        val action: String,
        val issue: Issue
)

data class Issue (
        val id: Int,
        val number: Int,
        val title: String,
        val createdAt: String?,
        val updatedAt: String?,
        val user: User
)

data class User(
        val login:String
)