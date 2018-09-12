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
import tech.jaya.octo.repository.Action
import tech.jaya.octo.repository.IssueEntity
import tech.jaya.octo.repository.IssueRepository
import java.util.*

class OctoServiceTest {
    lateinit var octoService: OctoService
    lateinit var issueRepository: IssueRepository

    @Before
    fun before() {
        issueRepository = mock { }
        octoService = OctoService(issueRepository)
    }

    @After
    fun tearDown() {
        Mockito.reset(issueRepository)
    }


    @Test
    fun `When receiving a IssueEvent object, save data on repository`() {
        val issueEvent = generateIssueEntity(Date())
        octoService.saveIssueEvent(issueEvent)
        verify(issueRepository, times(1)).save(issueEvent)
    }

    @Test
    fun `When call getEventsByNumber, return a String with IssueEvent info`() {
        val expectedIssueEventListResult = "[{\"action\":\"REOPENED\",\"id\":359026973,\"number\":2,\"title\":\"Teste 2\",\"createdAt\":\"Feb 1, 2018 12:00:00 AM\",\"updatedAt\":\"Feb 1, 2018 12:00:00 AM\",\"login\":\"fulanodasilva\"}]"
        whenever(issueRepository.getAllByNumber(2)).thenReturn(listOf(generateIssueEntity(GregorianCalendar(2018, 1,1).time)))
        expect(octoService.getEventsByNumber(2)).toBe(expectedIssueEventListResult)
    }

    @Test
    fun `Return the statistics from the repository`() {
        val expectedStatisticsResult = "{\"closed\":2,\"opened\":2}"
        whenever(issueRepository.getAll()).thenReturn(generateIssueEntityList())
        expect(octoService.getStatistics()).toBe(expectedStatisticsResult)
    }

    private fun generateIssueEntity(date: Date) =
            IssueEntity(action = Action.REOPENED, id = 359026973, number = 2, title = "Teste 2", createdAt = date, updatedAt = date, login = "fulanodasilva")

    private fun generateIssueEntityList(): List<IssueEntity> {
        var baseCalendar = GregorianCalendar(2018, 1,1)

        fun getIncrementedDate(): Date{
            baseCalendar.add(Calendar.DAY_OF_MONTH, 1)
            return baseCalendar.time
        }

        return listOf(
            IssueEntity(action= Action.OPENED, id=359026971, number=1, title="Teste 1", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),
            IssueEntity(action= Action.CLOSED, id=359026971, number=1, title="Teste 1", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),

            IssueEntity(action= Action.OPENED, id=359026972, number=2, title="Teste 2", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),
            IssueEntity(action= Action.CLOSED, id=359026972, number=2, title="Teste 2", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),
            IssueEntity(action= Action.REOPENED, id=359026972, number=2, title="Teste 2", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),

            IssueEntity(action= Action.OPENED, id=359026973, number=3, title="Teste 3", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),
            IssueEntity(action= Action.CLOSED, id=359026973, number=3, title="Teste 3", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),
            IssueEntity(action= Action.REOPENED, id=359026973, number=3, title="Teste 3", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),
            IssueEntity(action= Action.CLOSED, id=359026973, number=3, title="Teste 3", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva"),

            IssueEntity(action= Action.OPENED, id=359026947, number=4, title="Teste 4", createdAt= getIncrementedDate(), updatedAt=getIncrementedDate(), login="fulanodasilva")
        )


    }
}

