package dev.matvenoid.routes.group

import dev.matvenoid.model.group.GroupRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.groupRoutes(repository: GroupRepository) {
    routing {
        route("/api/groups") {
            getGroupRoutes(repository)
            insertGroupRoutes(repository)
            updateGroupRoutes(repository)
            deleteGroupRoutes(repository)
        }
    }
}