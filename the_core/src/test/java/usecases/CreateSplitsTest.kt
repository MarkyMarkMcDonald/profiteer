package usecases

import org.junit.Test
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before

class CreateSplitsTest {

    lateinit var guiSpy: SplitsGuiSpy
    lateinit var fakeTrackRepository: TrackRepository
    lateinit var fakeSplitRepository: SplitRepository
    
    @Before
    fun setUp() {
        guiSpy = SplitsGuiSpy()
        fakeTrackRepository = InMemoryTrackRepository()
        fakeSplitRepository = InMemorySplitRepository()
    }

    @Test
    fun create_split_requires_a_percentage() {
        
    }

    @Test
    fun sends_an_id_for_the_split_back_to_the_gui() {
    }

    @Test
    fun splits_are_unique_by_track_and_recipient() {
    }

    class SplitsGuiSpy : SplitsGui {

    }


}

