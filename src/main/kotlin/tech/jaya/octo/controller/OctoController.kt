package tech.jaya.octo

import com.google.gson.GsonBuilder
import io.javalin.Javalin
import io.javalin.json.FromJsonMapper
import io.javalin.json.JavalinJson
import io.javalin.json.ToJsonMapper
import tech.jaya.octo.endpoints.EventsEndpoints
import tech.jaya.octo.endpoints.WebhookEndpoints
import tech.jaya.octo.repository.impl.IssueRepositoryMemory
import tech.jaya.octo.service.OctoService

class OctoController {
    fun startAplication(){
        val issueRepository = IssueRepositoryMemory()
        val octoService = OctoService(issueRepository)
        val app = Javalin.create().start(7000)

        configureJsonMapper()

        app.routes {
            EventsEndpoints(octoService).addEndpoints()
            WebhookEndpoints(octoService).addEndpoints()
        }
    }

    private fun configureJsonMapper() {
        val gson = GsonBuilder().create()
        JavalinJson.fromJsonMapper = object : FromJsonMapper {
            override fun <T> map(json: String, targetClass: Class<T>): T = gson.fromJson(json, targetClass)
        }

        JavalinJson.toJsonMapper = object : ToJsonMapper {
            override fun map(obj: Any): String = gson.toJson(obj)
        }
    }
}