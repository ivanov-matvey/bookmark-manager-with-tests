package dev.matvenoid.routes.tag

import dev.matvenoid.model.tag.TagRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.tagRoutes(repository: TagRepository) {
    routing {
        route("/api/tags") {
            getTagRoutes(repository)
            insertTagRoutes(repository)
            updateTagRoutes(repository)
            deleteTagRoutes(repository)
        }
    }
}