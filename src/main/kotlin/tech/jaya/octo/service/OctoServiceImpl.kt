package tech.jaya.octo.service

import com.google.gson.GsonBuilder
import tech.jaya.octo.model.Action
import tech.jaya.octo.model.IssueEntity
import tech.jaya.octo.repository.IssueRepository

class OctoServiceImpl(
        private val issueRepository: IssueRepository
): OctoService {
    private val gson = GsonBuilder().create()

    override fun getEventsByNumber(number: Int): String = gson.toJson(issueRepository.getAllByNumber(number))

    override fun getStatistics(): String = gson.toJson(getActionCount(issueRepository.getAll()))

    override fun saveIssueEvent(issueEntity: IssueEntity){
        println("Saving:\n $issueEntity ")
        issueRepository.save(issueEntity)
    }

    private fun getActionCount(issues: List<IssueEntity>): Map<Action, Int>{
        var count = mutableMapOf(Action.OPENED to 0, Action.CLOSED to 0)
        issues.groupBy { it.id }.forEach{
            when(it.value.maxBy { it.updatedAt!!.time }?.action){
                Action.CLOSED -> count[Action.CLOSED] = 1 + count[Action.CLOSED]!!
                Action.OPENED, Action.REOPENED -> count[Action.OPENED] = 1 + count[Action.OPENED]!!
            }
        }
        return count
    }


}