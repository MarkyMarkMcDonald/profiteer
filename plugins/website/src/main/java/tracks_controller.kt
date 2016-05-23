import kotlinx.html.*
import kotlinx.html.FormMethod.post
import kotlinx.html.stream.createHTML
import spark.Response
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
                    tracksList(trackRepository.all())
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
            create_track(TrackCreatedObserver(response), trackRepository, Track(title))
            response.body()
        }
    }
}

private fun BODY.tracksList(tracks: Collection<Track>) {
    ul {
        tracks.forEach { track ->
            li {
                +track.title
            }
        }
    }
}

private fun BODY.trackForm() {
    h1 { +"Add Track" }
    form(method = post, action = "/tracks") {
        label {
            +"Title"
            input(type = InputType.text, name = "title")
        }
        submitInput(name = "create")
    }
}

class TrackCreatedObserver(private val response: Response) : Gui {

    override fun validationFailed(message: String) {
        val responseBody = createHTML().html {
            body {
                h1 { +"Failed to add track: $message"}
                trackForm()
            }
        }
        response.status(400)
        response.body(responseBody)
    }

    override fun trackCreated(id: Long) {
        response.redirect("/tracks")
    }
}
