package tech.jaya.octo

import org.koin.dsl.module.module
import tech.jaya.octo.repository.IssueRepository
import tech.jaya.octo.repository.impl.IssueRepositoryMongoDB
import tech.jaya.octo.service.OctoService
import tech.jaya.octo.service.OctoServiceImpl

val octoModule = module {
    single<OctoService> { OctoServiceImpl(get()) }
    single<IssueRepository> { IssueRepositoryMongoDB() }
}