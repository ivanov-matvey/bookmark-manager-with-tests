package dev.matvenoid.routes.bookmark

import dev.matvenoid.model.bookmark.BookmarkRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.bookmarkRoutes(repository: BookmarkRepository) {
    routing {
        route("/api/bookmarks") {
            getBookmarkRoutes(repository)
            insertBookmarkRoutes(repository)
            updateBookmarkRoutes(repository)
            deleteBookmarkRoutes(repository)        }
    }
}