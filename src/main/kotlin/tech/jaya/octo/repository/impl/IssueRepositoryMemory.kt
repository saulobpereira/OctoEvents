package tech.jaya.octo.repository.impl

import tech.jaya.octo.model.IssueEntity
import tech.jaya.octo.repository.IssueRepository

class IssueRepositoryMemory: IssueRepository {
    var issues = mutableListOf<IssueEntity>()
    override fun save(issueEntity: IssueEntity) {
        issues.add(issueEntity)
    }

    override fun getAllByNumber(number: Int): List<IssueEntity> {
        return issues.filter { it.number == number }
    }

    override fun getAll(): List<IssueEntity> {
        return issues
    }
}