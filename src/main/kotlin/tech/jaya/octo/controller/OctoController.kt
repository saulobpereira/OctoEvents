package tech.jaya.octo

import com.google.gson.GsonBuilder
import io.javalin.Javalin
import io.javalin.json.FromJsonMapper
import io.javalin.json.JavalinJson
import io.javalin.json.ToJsonMapper
import tech.jaya.octo.repository.EventsEndpoints

class OctoController {
    fun startAplication(){
        val octoService = OctoService()
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