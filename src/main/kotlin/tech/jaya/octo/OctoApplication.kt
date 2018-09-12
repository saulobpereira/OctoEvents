package tech.jaya.octo

import tech.jaya.octo.controller.OctoController

class OctoApplication{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            OctoController().startAplication()
        }
    }
}



