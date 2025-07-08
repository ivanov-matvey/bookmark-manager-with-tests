package dev.matvenoid.routes.group

import dev.matvenoid.model.group.Group
import dev.matvenoid.model.group.GroupRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertGroupRoutes(repository: GroupRepository) {
    post {
        val group = call.receive<Group>()
        repository.insert(group)

        call.respond(HttpStatusCode.Created)
    }
}