package controller

import org.koin.dsl.module.module
import tech.jaya.octo.repository.IssueRepository
import tech.jaya.octo.repository.impl.IssueRepositoryMemory
import tech.jaya.octo.service.OctoService
import tech.jaya.octo.service.OctoServiceImpl

val octoModuleTest = module {
    single<OctoService> { OctoServiceImpl(get()) }
    single<IssueRepository> { IssueRepositoryMemory() }
}