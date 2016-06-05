package templates

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import usecases.Track

fun index(tracks: Collection<Track>): String {
    return createHTML().html {
        body {
            trackForm()
            tracksList(tracks)
        }
    }
}

fun new(): String {
    return createHTML().html {
        body {
            trackForm()
        }
    }
}

fun trackCreationFailure(message: String): String {
    return createHTML().html {
        body {
            h1 { +"Failed to add track: $message" }
            trackForm()
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
    form(method = FormMethod.post, action = "/tracks") {
        label {
            +"Title"
            input(type = InputType.text, name = "title")
        }
        submitInput(name = "create")
    }
}