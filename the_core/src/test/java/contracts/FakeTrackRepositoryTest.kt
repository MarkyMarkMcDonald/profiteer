package contracts

import usecases.FakeTrackRepository
import usecases.TrackRepository

class FakeTrackRepositoryTest : TrackRepositoryTest() {

    override fun getRepository(): TrackRepository {
        return FakeTrackRepository()
    }

}
