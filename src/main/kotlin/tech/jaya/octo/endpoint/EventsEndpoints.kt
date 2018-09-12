package tech.jaya.octo.endpoint

import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.EndpointGroup
import tech.jaya.octo.service.OctoService

class EventsEndpoints(val octoService: OctoService): EndpointGroup {
    override fun addEndpoints() {
        path("issues") {
            get(":event_number/events") { ctx ->
                val number = ctx.pathParam("event_number").toInt()
                ctx.result(octoService.getEventsByNumber(number))
            }
            get("statistics") { ctx ->
                ctx.result(octoService.getStatistics())
            }
        }
    }

}