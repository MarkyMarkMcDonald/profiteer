package usecases

import java.util.*

class InMemoryTrackRepository : TrackRepository {
    private val tracks: MutableList<Track> = mutableListOf()

    override fun save(track: Track): Long {
        val randomId = Random().nextLong()
        track.id = randomId
        tracks.add(track)
        return randomId
    }

    override fun findByTitle(title: String): Track? {
        return tracks.find { it.title.equals(title) }
    }
}
