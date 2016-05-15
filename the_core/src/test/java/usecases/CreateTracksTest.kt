package usecases

import org.junit.Test
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import java.util.*

class ImportTracksTest {

    var guiSpy = GuiSpy()
    var fakeTrackRepository = FakeTrackRepository()

    @Before
    fun setUp() {
        guiSpy = GuiSpy()
        fakeTrackRepository = FakeTrackRepository()
    }

    @Test
    fun create_track_requires_titles() {
        create_track(guiSpy, fakeTrackRepository, Track(title=""))
        assertThat(guiSpy.validationFailedCalls).asList().contains("Tracks must have a title")
    }

    @Test
    fun sends_an_id_for_the_track_back_to_the_gui() {
        create_track(guiSpy, fakeTrackRepository, Track(title="Of The Wrist"))
        assertThat(guiSpy.createdTrackId).isNotNull()
    }

    @Test
    fun tracks_are_unique_by_name() {
        create_track(guiSpy, fakeTrackRepository, Track(title="Of The Wrist"))
        val firstId = guiSpy.createdTrackId
        create_track(guiSpy, fakeTrackRepository, Track(title="Of The Wrist"))

        assertThat(guiSpy.createdTrackId).isEqualTo(firstId)
    }
}

class FakeTrackRepository : TrackRepository {
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

class GuiSpy : Gui {
    var createdTrackId: Long? = null

    val validationFailedCalls: MutableList<String> = mutableListOf()

    override fun trackCreated(id: Long) {
        createdTrackId = id
    }

    override fun validationFailed(message: String) {
        validationFailedCalls.add(message)
    }
}
