package dev.matvenoid

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.exceptions.GroupNotFoundException
import dev.matvenoid.exceptions.TagNotFoundException
import dev.matvenoid.exceptions.BookmarkNotFoundException
import dev.matvenoid.model.bookmark.BookmarkRepository
import dev.matvenoid.model.group.GroupRepository
import dev.matvenoid.model.tag.TagRepository
import dev.matvenoid.routes.bookmark.bookmarkRoutes
import dev.matvenoid.routes.group.groupRoutes
import dev.matvenoid.routes.tag.tagRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.*
import io.ktor.server.routing.routing

fun Application.configureRouting(
    groupRepository: GroupRepository,
    tagRepository: TagRepository,
    bookmarkRepository: BookmarkRepository
) {
    install(ContentNegotiation) {
        json()
    }
    install(StatusPages) {
        exception<NotFoundException> { call, cause ->
            call.respondText(
                text = cause.message ?: "Unknown error",
                status = HttpStatusCode.NotFound
            )
        }
        exception<IllegalArgumentException> { call, cause ->
            call.respondText(
                text = cause.message ?: "Invalid Request",
                status = HttpStatusCode.BadRequest
            )
        }

        exception<GroupNotFoundException> { call, cause ->
            call.respondText(
                text = cause.message,
                status = HttpStatusCode.NotFound
            )
        }
        exception<TagNotFoundException> { call, cause ->
            call.respondText(
                text = cause.message,
                status = HttpStatusCode.NotFound
            )
        }
        exception<BookmarkNotFoundException> { call, cause ->
            call.respondText(
                text = cause.message,
                status = HttpStatusCode.NotFound
            )
        }
        exception<InvalidIdFormatException> { call, cause ->
            call.respondText(
                text = cause.message,
                status = HttpStatusCode.BadRequest
            )
        }
    }
    groupRoutes(groupRepository)
    tagRoutes(tagRepository)
    bookmarkRoutes(bookmarkRepository)

    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
    }
}
