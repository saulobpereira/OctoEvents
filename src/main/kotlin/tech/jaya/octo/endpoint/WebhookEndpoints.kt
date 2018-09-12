package tech.jaya.octo.endpoint

import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.EndpointGroup
import tech.jaya.octo.endpoint.model.IssueRequest
import tech.jaya.octo.model.Action
import tech.jaya.octo.model.IssueEntity
import tech.jaya.octo.service.OctoService

class WebhookEndpoints(val octoService: OctoService): EndpointGroup {
    override fun addEndpoints() {
        post("/issue"){ ctx ->
            val request = ctx.bodyAsClass(IssueRequest::class.java)
            octoService.saveIssueEvent(issueRequestAsIssueEntity(request))
        }
    }
}

private fun issueRequestAsIssueEntity(issueRequest: IssueRequest) = IssueEntity(
        Action.valueOf(issueRequest.action.toUpperCase()),
        issueRequest.issue.id,
        issueRequest.issue.number,
        issueRequest.issue.title,
        issueRequest.issue.createdAt,
        issueRequest.issue.updatedAt,
        issueRequest.issue.user.login
)