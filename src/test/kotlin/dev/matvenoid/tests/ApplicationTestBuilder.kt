package dev.matvenoid.tests

import dev.matvenoid.configureRouting
import dev.matvenoid.repositories.group.GroupFakeRepository
import dev.matvenoid.repositories.tag.TagFakeRepository
import dev.matvenoid.repositories.bookmark.BookmarkFakeRepository
import io.ktor.server.testing.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

fun ApplicationTestBuilder.configureApp() {
    application {
        configureRouting(
            groupRepository = GroupFakeRepository(),
            tagRepository = TagFakeRepository(),
            bookmarkRepository = BookmarkFakeRepository()
        )
    }
}

fun ApplicationTestBuilder.jsonClient() = createClient {
    install(ContentNegotiation) {
        json()
    }
}
