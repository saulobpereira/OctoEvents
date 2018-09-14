package tech.jaya.octo.service

import org.koin.dsl.module.module
import tech.jaya.octo.repository.IssueRepository
import tech.jaya.octo.repository.impl.IssueRepositoryMemory

val octoModuleTest = module {
    single<OctoService> { OctoServiceImpl(get()) }
    single<IssueRepository> { IssueRepositoryMemory() }
}