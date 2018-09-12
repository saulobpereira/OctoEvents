package tech.jaya.octo.controller

import com.google.gson.GsonBuilder
import io.javalin.Javalin
import io.javalin.json.FromJsonMapper
import io.javalin.json.JavalinJson
import io.javalin.json.ToJsonMapper
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import tech.jaya.octo.endpoint.EventsEndpoints
import tech.jaya.octo.endpoint.WebhookEndpoints
import tech.jaya.octo.service.OctoService

class OctoController: KoinComponent {

    private val octoService by inject<OctoService>()

    fun startAplication(){
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