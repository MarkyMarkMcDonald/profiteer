package contracts

import org.assertj.core.api.KotlinAssertions
import org.junit.Before
import org.junit.Test
import usecases.FakeTrackRepository
import usecases.Track
import usecases.TrackRepository

abstract class TrackRepositoryTest {

    var trackRepository = getRepository()
    val sinking = Track("Sinking")
    val turnToSalt = Track("Turn to Salt")

    abstract fun getRepository(): TrackRepository

    @Before
    fun setUp() {
        trackRepository = getRepository()
        trackRepository.save(sinking)
        trackRepository.save(turnToSalt)
    }

    @Test
    fun finds_by_title() {
        KotlinAssertions.assertThat(trackRepository.findByTitle("Turn to Salt")!!.title).isEqualTo("Turn to Salt")
        KotlinAssertions.assertThat(trackRepository.findByTitle("Sinking")!!.title).isEqualTo("Sinking")
    }

    @Test
    fun creates_unique_ids() {
        KotlinAssertions.assertThat(sinking.id).isNotNull()
        KotlinAssertions.assertThat(turnToSalt.id).isNotNull()

        KotlinAssertions.assertThat(sinking.id).isNotEqualTo(turnToSalt.id)
    }
}