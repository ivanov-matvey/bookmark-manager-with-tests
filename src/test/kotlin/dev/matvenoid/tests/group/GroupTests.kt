package dev.matvenoid.tests.group

import dev.matvenoid.model.group.Group
import dev.matvenoid.tests.configureApp
import dev.matvenoid.tests.jsonClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class GroupTests {
    @Test
    fun getAllGroups_ValidRequest_ReturnsAllGroups() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/groups")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualGroups = response.body<List<Group>>()
        val expectedGroups = listOf(
            Group(1, "Browsers"),
            Group(2, "Services"),
            Group(3, "Messengers")
        )
        assertContentEquals(expectedGroups, actualGroups)
    }

    @Test
    fun getGroupById_ValidRequest_ReturnsGroup() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/groups/2")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualGroup = response.body<Group>()
        val expectedGroup = Group(2, "Services")
        assertEquals(expectedGroup, actualGroup)
    }

    @Test
    fun getGroupById_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/groups/invalidId")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun getGroupById_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/groups/4")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Group 4 does not exist", response.bodyAsText())
    }

    @Test
    fun createGroup_ValidRequest_ReturnsCreated() = testApplication {
        configureApp()
        val client = jsonClient()

        val newGroup = Group(null, "TestGroup")
        val responseCreate = client.post("/api/groups") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(newGroup)
        }
        assertEquals(HttpStatusCode.Created, responseCreate.status)

        val responseGetAll = client.get("/api/groups")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualGroups = responseGetAll.body<List<Group>>()
        val expectedGroups = listOf(
            Group(1, "Browsers"),
            Group(2, "Services"),
            Group(3, "Messengers"),
            Group(4, "TestGroup")
        )
        assertContentEquals(expectedGroups, actualGroups)
    }

    @Test
    fun updateGroup_ValidRequest_ReturnsOK() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedGroup = Group(null, "TestGroup")
        val responseUpdate = client.put("/api/groups/1") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedGroup)
        }
        assertEquals(HttpStatusCode.OK, responseUpdate.status)

        val responseGetAll = client.get("/api/groups")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualGroups = responseGetAll.body<List<Group>>()
        val expectedGroups = listOf(
            Group(1, "TestGroup"),
            Group(2, "Services"),
            Group(3, "Messengers"),
        )
        assertContentEquals(expectedGroups, actualGroups)
    }

    @Test
    fun updateGroup_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedGroup = Group(null, "TestGroup")
        val response = client.put("/api/groups/invalidId") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedGroup)
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun updateGroup_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedGroup = Group(null, "TestGroup")
        val response = client.put("/api/groups/4") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedGroup)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Group 4 does not exist", response.bodyAsText())
    }

    @Test
    fun deleteGroup_ValidRequest_ReturnsNoContent() = testApplication {
        configureApp()
        val client = jsonClient()

        val responseDelete = client.delete("/api/groups/2")
        assertEquals(HttpStatusCode.NoContent, responseDelete.status)

        val responseGetAll = client.get("/api/groups")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualGroups = responseGetAll.body<List<Group>>()
        val expectedGroups = listOf(
            Group(1, "Browsers"),
            Group(3, "Messengers")
        )
        assertContentEquals(expectedGroups, actualGroups)
    }

    @Test
    fun deleteGroup_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.delete("/api/groups/invalidId")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun deleteGroup_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.delete("/api/groups/4")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Group 4 does not exist", response.bodyAsText())
    }
}
