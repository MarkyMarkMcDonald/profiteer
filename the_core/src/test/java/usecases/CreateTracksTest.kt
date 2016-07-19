package usecases

import org.junit.Test
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before

class ImportTracksTest {

    lateinit var guiSpy: SplitsGuiSpy
    lateinit var fakeTrackRepository: TrackRepository

    @Before
    fun setUp() {
        guiSpy = SplitsGuiSpy()
        fakeTrackRepository = InMemoryTrackRepository()
    }

    @Test
    fun create_track_requires_titles() {
        create_track(guiSpy, fakeTrackRepository, Track(title = ""))
        assertThat(guiSpy.validationFailedCalls).asList().contains("Tracks must have a title")
    }

    @Test
    fun sends_an_id_for_the_track_back_to_the_gui() {
        create_track(guiSpy, fakeTrackRepository, Track(title = "Of The Wrist"))
        assertThat(guiSpy.createdTrackId).isNotNull()
    }

    @Test
    fun tracks_are_unique_by_name() {
        create_track(guiSpy, fakeTrackRepository, Track(title = "Of The Wrist"))
        val firstId = guiSpy.createdTrackId
        create_track(guiSpy, fakeTrackRepository, Track(title = "Of The Wrist"))

        assertThat(guiSpy.validationFailedCalls).asList().contains("Tracks must be unique")
        assertThat(guiSpy.createdTrackId).isEqualTo(firstId)
    }
}

class SplitsGuiSpy : SplitsGui {
    var createdTrackId: Long? = null

    val validationFailedCalls: MutableList<String> = mutableListOf()

    override fun trackCreated(id: Long) {
        createdTrackId = id
    }

    override fun validationFailed(message: String) {
        validationFailedCalls.add(message)
    }
}
