package dev.matvenoid.routes.bookmark

import dev.matvenoid.model.bookmark.Bookmark
import dev.matvenoid.model.bookmark.BookmarkRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertBookmarkRoutes(repository: BookmarkRepository) {
    post {
        val bookmark = call.receive<Bookmark>()
        repository.insert(bookmark)

        call.respond(HttpStatusCode.Created)
    }
}