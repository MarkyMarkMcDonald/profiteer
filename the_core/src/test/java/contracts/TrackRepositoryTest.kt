package contracts

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import org.junit.Test
import usecases.Track
import usecases.TrackRepository

abstract class TrackRepositoryTest {

    lateinit var trackRepository: TrackRepository
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
        assertThat(trackRepository.findByTitle("Turn to Salt")!!.title).isEqualTo("Turn to Salt")
        assertThat(trackRepository.findByTitle("Sinking")!!.title).isEqualTo("Sinking")
    }

    @Test
    fun creates_unique_ids() {
        assertThat(sinking.id).isNotNull()
        assertThat(turnToSalt.id).isNotNull()

        assertThat(sinking.id).isNotEqualTo(turnToSalt.id)
    }

    @Test
    fun finds_all() {
        trackRepository = getRepository()
        assertThat(trackRepository.all()).asList().hasSize(0)

        trackRepository.save(Track("First Track"))
        assertThat(trackRepository.all()).asList().hasSize(1)

        trackRepository.save(Track("Second Track"))
        assertThat(trackRepository.all()).asList().hasSize(2)
    }
}