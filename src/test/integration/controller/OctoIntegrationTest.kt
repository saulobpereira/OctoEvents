package controller

import com.nhaarman.expect.expect
import io.javalin.Javalin
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import tech.jaya.octo.controller.OctoController
import tech.jaya.octo.model.Action
import tech.jaya.octo.model.IssueEntity
import tech.jaya.octo.repository.IssueRepository
import tech.jaya.octo.repository.impl.IssueRepositoryMemory
import java.util.*

class OctoIntegrationTest: KoinTest {
    private lateinit var app: Javalin
    private val url = "http://localhost:8000/"

    private val issueRepository by inject<IssueRepository>()

    companion object {
        private const val ISSUE_WEBHOOK_PAYLOAD: String = "/issue_webhook_test_payload.json"
    }

    @Before
    fun setUp() {
        startKoin(listOf(octoModuleTest))
        app = OctoController(8000).startAplication()
    }

    @After
    fun tearDown() {
        stopKoin()
        app.stop()
    }

    @Test
    fun `Receive a webhook payload, process an save an IssueEvent to Repository`() {
        khttp.post(url = "${url}issue", data = readWebhookPayload())
        expect(issueRepository.getAll().first()).toBe(generateIssueEntity(GregorianCalendar(2018, 0,1).time))
    }

    @Test
    fun `Get Events of an Issue from repository`(){
        (issueRepository as IssueRepositoryMemory ).issues = mutableListOf(generateIssueEntity(GregorianCalendar(2018, 0,1).time))
        val expectedResponseJson = JSONArray("[{\"action\":\"CLOSED\",\"id\":357336075,\"number\":1,\"title\":\"Teste\",\"createdAt\":\"Jan 1, 2018 12:00:00 AM\",\"updatedAt\":\"Jan 1, 2018 12:00:00 AM\",\"login\":\"saulobpereira\"}]")
        val response = khttp.get(url = "${url}issues/1/events")
        expect(response.jsonArray.toString()).toBe(expectedResponseJson.toString())
    }

    @Test
    fun `Get Statistics from repository`(){
        (issueRepository as IssueRepositoryMemory ).issues = generateIssueEntityList()
        val expectedResponseJson = JSONObject("{\"opened\":2,\"closed\":2}")
        val response = khttp.get(url = "${url}issues/statistics")
        expect(response.jsonObject.toString()).toBe(expectedResponseJson.toString())
    }

    private fun readWebhookPayload(): JSONObject {
        return JSONObject(OctoIntegrationTest::class.java.getResource(ISSUE_WEBHOOK_PAYLOAD).readText())
    }

    private fun generateIssueEntity(date: Date) =
            IssueEntity(action = Action.CLOSED, id = 357336075, number = 1, title = "Teste", createdAt = date, updatedAt = date, login = "saulobpereira")


    private fun generateIssueEntityList(): MutableList<IssueEntity> {
        var baseCalendar = GregorianCalendar(2018, 1,1)

        fun getIncrementedDate(): Date{
            baseCalendar.add(Calendar.DAY_OF_MONTH, 1)
            return baseCalendar.time
        }

        return mutableListOf(
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