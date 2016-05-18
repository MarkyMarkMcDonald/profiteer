import kotlinx.html.*
import kotlinx.html.FormMethod.post
import kotlinx.html.stream.createHTML
import spark.Spark
import spark.Spark.get
import spark.Spark.post
import usecases.Gui
import usecases.InMemoryTrackRepository
import usecases.Track
import usecases.create_track

object Router {

    val trackRepository = InMemoryTrackRepository()

    @JvmStatic fun main(args: Array<String>) {
        val portOverride: String? = System.getenv("PORT")
        if (portOverride != null) {
            Spark.port(portOverride.toInt())
        }

        get("/tracks") { request, response ->
            createHTML().html {
                body {
                    trackForm()
                }
            }
        }

        get("/tracks/new") { request, response ->
            createHTML().html {
                body {
                    trackForm()
                }
            }
        }

        post("/tracks") { request, response ->
            val title = request.queryParams("title")
            val gui = ResponseSwitch()
            create_track(gui, trackRepository, Track(title))
            gui.responseBody
        }
    }

}

private fun BODY.trackForm() {
    h1 { +"New Track" }
    form(method = post, action = "/tracks") {
        label {
            +"Title"
            input(type = InputType.text, name = "title")
        }
        submitInput(name = "create")
    }
}

class ResponseSwitch() : Gui {
    var responseBody: Any? = null

    override fun validationFailed(message: String) {
        responseBody = createHTML().html {
            body {
                h1 { +"Failed to create track: $message"}
                trackForm()
            }
        }
    }

    override fun trackCreated(id: Long) {
        responseBody = createHTML().html {
            body {
                h1 { +"Created Track! $id"}
                trackForm()
            }
        }
    }
}
