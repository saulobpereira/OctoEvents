package tech.jaya.octo.service

import com.nhaarman.expect.expect
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import tech.jaya.octo.endpoints.model.Issue
import tech.jaya.octo.repository.Action
import tech.jaya.octo.repository.IssueEntity
import tech.jaya.octo.repository.IssueRepository
import java.util.*

class OctoServiceTest {
    lateinit var octoService: OctoService
    lateinit var issueRepository: IssueRepository

    @Before
    fun before(){
        issueRepository = mock {  }
        octoService = OctoService(issueRepository)
    }

    @After
    fun tearDown() {
        Mockito.reset(issueRepository)
    }


    @Test
    fun `When receiving a IssueEvent object, save data on repository`(){
        val issueEvent = generateIssueEntity(Date())
        octoService.saveIssueEvent(issueEvent)
        verify(issueRepository, times(1)).save(issueEvent)
    }

    @Test
    fun `When call getEventsByNumber, return a String with IssueEvent info`(){
        val date = Date()
        val expectedString  = "[IssueEntity(action=REOPENED, id=359026973, number=2, title=Teste 2, createdAt=$date, updatedAt=$date, login=fulanodasilva)]"
        whenever(issueRepository.getAllByNumber(2)).thenReturn(listOf(generateIssueEntity(date)))
        expect(octoService.getEventsByNumber(2)).toBe(expectedString)
    }

    @Test
    fun `Return the statistics from the repository`(){
        val expectedString = ""

    }

    private fun generateIssueEntity(date: Date) =
        IssueEntity(action= Action.REOPENED, id=359026973, number=2, title="Teste 2", createdAt= date, updatedAt=date, login="fulanodasilva")

}