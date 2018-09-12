package tech.jaya.octo.service

import tech.jaya.octo.model.IssueEntity

interface OctoService {
    fun getEventsByNumber(number: Int): String
    fun getStatistics(): String
    fun saveIssueEvent(issueEntity: IssueEntity)
}