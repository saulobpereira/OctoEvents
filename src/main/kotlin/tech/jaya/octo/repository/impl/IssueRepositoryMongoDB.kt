package tech.jaya.octo.repository.impl

import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import tech.jaya.octo.model.IssueEntity
import tech.jaya.octo.repository.IssueRepository

class IssueRepositoryMongoDB: IssueRepository {
    private val client = KMongo.createClient()
    private val database = client.getDatabase("octo")
    private val col = database.getCollection<IssueEntity>()

    override fun save(issueEntity: IssueEntity) {
        col.insertOne(issueEntity)
    }

    override fun getAllByNumber(number: Int): List<IssueEntity> {
        return col.find(IssueEntity::number eq number).toList()
    }

    override fun getAll(): List<IssueEntity> {
        return col.find().toList()
    }
}