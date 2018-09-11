package tech.jaya.octo.repository

class IssueRepositoryMemory:IssueRepository {
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