package tech.jaya.octo.repository

import tech.jaya.octo.model.IssueEntity

interface IssueRepository {

    fun save(issueEntity: IssueEntity)
    fun getAllByNumber(number: Int): List<IssueEntity>
    fun getAll(): List<IssueEntity>
}