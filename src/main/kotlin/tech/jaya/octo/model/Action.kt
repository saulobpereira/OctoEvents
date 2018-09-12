package tech.jaya.octo.model

enum class Action {
    OPENED,
    CLOSED,
    REOPENED;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}