package contracts

import usecases.InMemoryTrackRepository
import usecases.TrackRepository

class FakeTrackRepositoryTest : TrackRepositoryTest() {

    override fun getRepository(): TrackRepository {
        return InMemoryTrackRepository()
    }

}
