package usecases

fun create_track(gui: Gui, trackRepository: TrackRepository, track: Track) {
    if (track.title.isEmpty()) {
        gui.validationFailed("Tracks must have a title")
        return
    }

    if (trackRepository.findByTitle(track.title) != null) {
        gui.validationFailed("Tracks must be unique")
        return
    }

    gui.trackCreated(trackRepository.save(track))
}

data class Track(val title: String) {
    var id: Long? = null
}

interface Gui {
    fun validationFailed(message: String)

    fun trackCreated(id: Long)
}

interface TrackRepository {
    fun findByTitle(title: String): Track?

    fun save(track: Track): Long

    fun all(): Collection<Track>
}