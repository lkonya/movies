package movies

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class PingController {

    @Get("/ping")
    fun ping(): String = """{"pong": true}"""
}
