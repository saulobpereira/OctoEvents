package tech.jaya.octo

import com.google.gson.annotations.SerializedName
import java.util.*

data class IssueRequest(
        @SerializedName("action") val action: String,
        @SerializedName("issue") val issue: Issue
)

data class Issue (
        @SerializedName("id") val id: Int,
        @SerializedName("number") val number: Int,
        @SerializedName("title") val title: String,
        @SerializedName("created_at") val createdAt: Date?,
        @SerializedName("updated_at") val updatedAt: Date?,
        @SerializedName("user") val user: User
)

data class User(
        @SerializedName("login") val login:String
)