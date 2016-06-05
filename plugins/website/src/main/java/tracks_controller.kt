import spark.Response
import spark.Spark
import spark.Spark.get
import spark.Spark.post
import templates.index
import templates.new
import templates.trackCreationFailure
import usecases.Gui
import usecases.InMemoryTrackRepository
import usecases.Track
import usecases.create_track

object Router {

    val trackRepository = InMemoryTrackRepository()
    val portOverride: String? = System.getenv("PORT")

    @JvmStatic fun main(args: Array<String>) {
        if (portOverride != null) {
            Spark.port(portOverride.toInt())
        }

        get("/tracks") { request, response ->
            index(trackRepository.all())
        }

        get("/tracks/new") { request, response ->
            new()
        }

        post("/tracks") { request, response ->
            val title = request.queryParams("title")
            create_track(TrackCreatedObserver(response), trackRepository, Track(title))
            response.body()
        }
    }
}

class TrackCreatedObserver(private val response: Response) : Gui {

    override fun validationFailed(message: String) {
        response.status(400)
        response.body(trackCreationFailure(message))
    }

    override fun trackCreated(id: Long) {
        response.redirect("/tracks")
    }
}
