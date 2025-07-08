package dev.matvenoid.tests.bookmark

import dev.matvenoid.model.bookmark.Bookmark
import dev.matvenoid.tests.configureApp
import dev.matvenoid.tests.jsonClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class BookmarkTests {
    @Test
    fun getAllBookmarks_ValidRequest_ReturnsAllBookmarks() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualBookmarks = response.body<List<Bookmark>>()
        val expectedBookmarks = listOf(
            Bookmark(1, "Google", "google.com", 1, 1),
            Bookmark(2, "Wikipedia", "wikipedia.com", 2, 1),
            Bookmark(3, "Yandex", "yandex.com", 1)
        )
        assertContentEquals(expectedBookmarks, actualBookmarks)
    }

    @Test
    fun getBookmarkById_ValidRequest_ReturnsBookmark() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/2")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualBookmark = response.body<Bookmark>()
        val expectedBookmark = Bookmark(2, "Wikipedia", "wikipedia.com", 2, 1)
        assertEquals(expectedBookmark, actualBookmark)
    }

    @Test
    fun getBookmarkById_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/invalidId")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun getBookmarkById_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/4")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Bookmark 4 does not exist", response.bodyAsText())
    }

    @Test
    fun getBookmarksByGroupId_ValidRequest_ReturnsBookmarks() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/byGroupId/1")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualBookmarks = response.body<List<Bookmark>>()
        val expectedBookmarks = listOf(
            Bookmark(1, "Google", "google.com", 1, 1),
            Bookmark(3, "Yandex", "yandex.com", 1)
        )
        assertEquals(expectedBookmarks, actualBookmarks)
    }

    @Test
    fun getBookmarksByGroupId_ValidRequestNoRecords_ReturnsEmptyBookmarks() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/byGroupId/3")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualBookmarks = response.body<List<Bookmark>>()
        val expectedBookmarks = emptyList<Bookmark>()
        assertEquals(expectedBookmarks, actualBookmarks)
    }

    @Test
    fun getBookmarksByGroupId_InvalidGroupId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/byGroupId/invalidId")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun getBookmarksByGroupId_NonExistingGroupId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/byGroupId/4")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Group 4 does not exist", response.bodyAsText())
    }

    @Test
    fun getBookmarksByTagId_ValidRequest_ReturnsBookmarks() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/byTagId/1")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualBookmarks = response.body<List<Bookmark>>()
        val expectedBookmarks = listOf(
            Bookmark(1, "Google", "google.com", 1, 1),
            Bookmark(2, "Wikipedia", "wikipedia.com", 2, 1),
        )
        assertEquals(expectedBookmarks, actualBookmarks)
    }

    @Test
    fun getBookmarksByTagId_ValidRequestNoRecords_ReturnsEmptyBookmarks() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/byTagId/3")
        assertEquals(HttpStatusCode.OK, response.status)

        val actualBookmarks = response.body<List<Bookmark>>()
        val expectedBookmarks = emptyList<Bookmark>()
        assertEquals(expectedBookmarks, actualBookmarks)
    }

    @Test
    fun getBookmarksByTagId_InvalidTagId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/byTagId/invalidId")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun getBookmarksByTagId_NonExistingTagId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.get("/api/bookmarks/byTagId/4")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Tag 4 does not exist", response.bodyAsText())
    }

    @Test
    fun createBookmark_ValidRequest_ReturnsCreated() = testApplication {
        configureApp()
        val client = jsonClient()

        val newBookmark = Bookmark(null, "TestBookmark", "test.com", 1, 1)
        val responseCreate = client.post("/api/bookmarks") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(newBookmark)
        }
        assertEquals(HttpStatusCode.Created, responseCreate.status)

        val responseGetAll = client.get("/api/bookmarks")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualBookmarks= responseGetAll.body<List<Bookmark>>()
        val expectedBookmarks = listOf(
            Bookmark(1, "Google", "google.com", 1, 1),
            Bookmark(2, "Wikipedia", "wikipedia.com", 2, 1),
            Bookmark(3, "Yandex", "yandex.com", 1),
            Bookmark(4, "TestBookmark", "test.com", 1, 1)
        )
        assertContentEquals(expectedBookmarks, actualBookmarks)
    }

    @Test
    fun createBookmark_NonExistingGroupId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val newBookmark = Bookmark(null, "TestBookmark", "test.com", 4, 1)
        val response = client.post("/api/bookmarks") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(newBookmark)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Group 4 does not exist", response.bodyAsText())
    }

    @Test
    fun createBookmark_NonExistingTagId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val newBookmark = Bookmark(null, "TestBookmark", "test.com", 1, 4)
        val response = client.post("/api/bookmarks") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(newBookmark)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Tag 4 does not exist", response.bodyAsText())
    }

    @Test
    fun updateBookmark_ValidRequest_ReturnsOK() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedBookmark = Bookmark(null, "TestBookmark", "test.com", 1, 1)
        val responseUpdate = client.put("/api/bookmarks/1") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedBookmark)
        }
        assertEquals(HttpStatusCode.OK, responseUpdate.status)

        val responseGetAll = client.get("/api/bookmarks")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualBookmarks = responseGetAll.body<List<Bookmark>>()
        val expectedGroups = listOf(
            Bookmark(1, "TestBookmark", "test.com", 1, 1),
            Bookmark(2, "Wikipedia", "wikipedia.com", 2, 1),
            Bookmark(3, "Yandex", "yandex.com", 1)
        )
        assertContentEquals(expectedGroups, actualBookmarks)
    }

    @Test
    fun updateBookmark_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedBookmark = Bookmark(null, "TestBookmark", "test.com", 1, 1)
        val response = client.put("/api/bookmarks/invalidId") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedBookmark)
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun updateBookmark_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedBookmark = Bookmark(null, "TestBookmark", "test.com", 1, 1)
        val response = client.put("/api/bookmarks/4") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedBookmark)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Bookmark 4 does not exist", response.bodyAsText())
    }

    @Test
    fun updateBookmark_NonExistingGroupId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedBookmark = Bookmark(null, "TestBookmark", "test.com", 4, 1)
        val response = client.put("/api/bookmarks/1") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedBookmark)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Group 4 does not exist", response.bodyAsText())
    }

    @Test
    fun updateBookmark_NonExistingTagId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val updatedBookmark = Bookmark(null, "TestBookmark", "test.com", 1, 4)
        val response = client.put("/api/bookmarks/1") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(updatedBookmark)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Tag 4 does not exist", response.bodyAsText())
    }

    @Test
    fun deleteBookmark_ValidRequest_ReturnsNoContent() = testApplication {
        configureApp()
        val client = jsonClient()

        val responseDelete = client.delete("/api/bookmarks/2")
        assertEquals(HttpStatusCode.NoContent, responseDelete.status)

        val responseGetAll = client.get("/api/bookmarks")
        assertEquals(HttpStatusCode.OK, responseGetAll.status)

        val actualBookmarks = responseGetAll.body<List<Bookmark>>()
        val expectedBookmarks = listOf(
            Bookmark(1, "Google", "google.com", 1, 1),
            Bookmark(3, "Yandex", "yandex.com", 1)
        )
        assertContentEquals(expectedBookmarks, actualBookmarks)
    }

    @Test
    fun deleteBookmark_InvalidId_ReturnsBadRequest() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.delete("/api/bookmarks/invalidId")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid id format", response.bodyAsText())
    }

    @Test
    fun deleteBookmark_NonExistingId_ReturnsNotFound() = testApplication {
        configureApp()
        val client = jsonClient()

        val response = client.delete("/api/bookmarks/4")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Bookmark 4 does not exist", response.bodyAsText())
    }
}
