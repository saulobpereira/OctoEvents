package tech.jaya.octo

import com.google.gson.GsonBuilder
import io.javalin.Javalin
import io.javalin.json.FromJsonMapper
import io.javalin.json.JavalinJson
import io.javalin.json.ToJsonMapper
import tech.jaya.octo.repository.Action
import tech.jaya.octo.repository.IssueEntity


fun main(args: Array<String>) {
    val octoService = OctoService()
    val app = Javalin.create().start(7000)

    val gson = GsonBuilder().create()
    JavalinJson.fromJsonMapper = object : FromJsonMapper {
        override fun <T> map(json: String, targetClass: Class<T>): T = gson.fromJson(json, targetClass)
    }

    JavalinJson.toJsonMapper = object : ToJsonMapper {
        override fun map(obj: Any): String = gson.toJson(obj)
    }

    app.get("/issues/:event_number/events") { ctx ->
        val number = ctx.pathParam("event_number").toInt()
        ctx.result(octoService.getEventsByNumber(number))
    }
    app.get("/issues/statistics") { ctx ->
        ctx.result(octoService.getStatistics())
    }
    app.post("/issue"){ ctx ->
        val request = ctx.bodyAsClass(IssueRequest::class.java)
        octoService.saveIssueEvent(issueRequestAsIssueEntity(request))
    }
}

fun issueRequestAsIssueEntity(issueRequest: IssueRequest) = IssueEntity(
        Action.valueOf(issueRequest.action.toUpperCase()),
        issueRequest.issue.id,
        issueRequest.issue.number,
        issueRequest.issue.title,
        issueRequest.issue.createdAt,
        issueRequest.issue.updatedAt,
        issueRequest.issue.user.login
)

