package dev.matvenoid.routes.tag

import dev.matvenoid.model.tag.Tag
import dev.matvenoid.model.tag.TagRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertTagRoutes(repository: TagRepository) {
    post {
        val tag = call.receive<Tag>()
        repository.insert(tag)

        call.respond(HttpStatusCode.Created)
    }
}