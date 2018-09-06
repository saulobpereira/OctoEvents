package tech.jaya.octo.repository

interface IssueRepository {

    fun save(issueEntity: IssueEntity)
    fun getAllByNumber(number: Int): List<IssueEntity>
    fun getAllByAction(action: Action): List<IssueEntity>
}