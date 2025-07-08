package dev.matvenoid.tests.tag

import dev.matvenoid.model.tag.Tag
import dev.matvenoid.tests.configureApp
import dev.matvenoid.tests.jsonClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class TagTests {
    @Test
    fun getAllTags_ValidRequest_ReturnsAllTags() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/tags")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualTags = response.body<List<Tag>>()
        val expectedTags = listOf(
            Tag(1, "Work"),
            Tag(2, "University"),
            Tag(3, "Relax")
        )
        assertContentEquals(expectedTags, actualTags)
    }

    @Test
    fun getTagById_ValidRequest_ReturnsTag() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/tags/2")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualTag = response.body<Tag>()
        val expectedTag = Tag(2, "University")
        assertEquals(expectedTag, actualTag)
    }

    @Test
    fun getTagById_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/tags/invalidId")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun getTagById_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/tags/4")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Tag 4 does not exist", response.bodyAsText())
    }

    @Test
    fun createTag_ValidRequest_ReturnsCreated() = testApplication {
        configureApp()
        val client = jsonClient()

        val newTag = Tag(null, "TestTag")
        val responseCreate = client.post("/api/tags") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(newTag)
        }
        assertEquals(HttpStatusCode.Created, responseCreate.status)

        val responseGetAll = client.get("/api/tags")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualTags = responseGetAll.body<List<Tag>>()
        val expectedTags = listOf(
            Tag(1, "Work"),
            Tag(2, "University"),
            Tag(3, "Relax"),
            Tag(4, "TestTag")
        )
        assertContentEquals(expectedTags, actualTags)
    }

    @Test
    fun updateTag_ValidRequest_ReturnsOK() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedTag = Tag(null, "TestTag")
        val responseUpdate = client.put("/api/tags/1") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedTag)
        }
        assertEquals(HttpStatusCode.OK, responseUpdate.status)

        val responseGetAll = client.get("/api/tags")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualTags = responseGetAll.body<List<Tag>>()
        val expectedTags = listOf(
            Tag(1, "TestTag"),
            Tag(2, "University"),
            Tag(3, "Relax")
        )
        assertContentEquals(expectedTags, actualTags)
    }

    @Test
    fun updateTag_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedTag = Tag(null, "TestTag")
        val response = client.put("/api/tags/invalidId") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedTag)
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun updateTag_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedTag = Tag(null, "TestTag")
        val response = client.put("/api/tags/4") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedTag)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Tag 4 does not exist", response.bodyAsText())
    }

    @Test
    fun deleteTag_ValidRequest_ReturnsNoContent() = testApplication {
        configureApp()
        val client = jsonClient()

        val responseDelete = client.delete("/api/tags/2")
        assertEquals(HttpStatusCode.NoContent, responseDelete.status)

        val responseGetAll = client.get("/api/tags")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualTags = responseGetAll.body<List<Tag>>()
        val expectedTags = listOf(
            Tag(1, "Work"),
            Tag(3, "Relax")
        )
        assertContentEquals(expectedTags, actualTags)
    }

    @Test
    fun deleteTag_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.delete("/api/tags/invalidId")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun deleteTag_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.delete("/api/tags/4")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Tag 4 does not exist", response.bodyAsText())
    }
}
