package tech.jaya.octo.repository

enum class Action {
    OPENED,
    CLOSED,
    REOPENED;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}