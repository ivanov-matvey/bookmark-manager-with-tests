package dev.matvenoid

import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
    val dotenv = dotenv()
    val url = dotenv["DB_URL"]
    val user = dotenv["DB_USER"]
    val password = dotenv["DB_PASSWORD"]

    Database.connect(
        url,
        user = user,
        password = password
    )
}
