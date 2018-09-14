package tech.jaya.octo

import org.koin.standalone.StandAloneContext.startKoin
import tech.jaya.octo.controller.OctoController

class OctoApplication{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            startKoin(listOf(octoModule))
            OctoController(7000).startAplication()
        }
    }
}



