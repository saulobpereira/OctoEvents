package tech.jaya.octo.service

import tech.jaya.octo.repository.Action
import tech.jaya.octo.repository.IssueEntity
import tech.jaya.octo.repository.impl.IssueRepositoryMemory

class OctoService {
    private val issueRepository = IssueRepositoryMemory()

    fun getEventsByNumber(number: Int): String{
        return issueRepository.getAllByNumber(number).toString()
    }

    fun getStatistics(): String{
        val count = getActionCount(issueRepository.getAll())
        return "{\n" +
                "\"open\": ${count[Action.OPENED]},\n" +
                "\"closed\": ${count[Action.CLOSED]},\n" +
                "}"
    }

    fun saveIssueEvent(issueEntity: IssueEntity){
        println("Saving:\n ${issueEntity} ")
        issueRepository.save(issueEntity)
    }

    private fun getActionCount(issues: List<IssueEntity>): Map<Action, Int>{
        var count = mutableMapOf(Action.CLOSED to 0, Action.OPENED to 0)
        issues.groupBy { it.id }.forEach{
            when(it.value.maxBy { it.updatedAt!!.time }?.action){
                Action.CLOSED -> count[Action.CLOSED] = 1 + count[Action.CLOSED]!!
                Action.OPENED, Action.REOPENED -> count[Action.OPENED] = 1 + count[Action.OPENED]!!
            }
        }
        return count
    }


}