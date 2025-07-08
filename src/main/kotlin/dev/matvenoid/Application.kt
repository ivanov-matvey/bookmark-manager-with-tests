package dev.matvenoid

import dev.matvenoid.model.bookmark.BookmarkPostgresRepository
import dev.matvenoid.model.group.GroupPostgresRepository
import dev.matvenoid.model.tag.TagPostgresRepository
import io.ktor.http.HttpHeaders
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.CORS

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }

    configureRouting(
        groupRepository = GroupPostgresRepository(),
        tagRepository = TagPostgresRepository(),
        bookmarkRepository = BookmarkPostgresRepository()
    )
}