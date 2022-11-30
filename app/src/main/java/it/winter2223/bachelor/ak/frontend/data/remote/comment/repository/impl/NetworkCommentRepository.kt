package it.winter2223.bachelor.ak.frontend.data.remote.comment.repository.impl

import it.winter2223.bachelor.ak.frontend.data.remote.comment.model.dto.CommentOutput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.repository.CommentRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class NetworkCommentRepository @Inject constructor() : CommentRepository {
    @Suppress("MaxLineLength")
    private val _comments = listOf(
        "im feeling rather rotten so im not very ambitious right now",
        "i explain why i clung to a relationship with a boy who was in many ways immature and uncommitted despite the excitement i should have been feeling for getting accepted into the masters program at the university of virginia",
        "im updating my blog because i feel shitty",
        "i never make her separate from me because i don t ever want her to feel like i m ashamed with her",
        "i left with my bouquet of red and yellow tulips under my arm feeling slightly more optimistic than when i arrived",
        "i was feeling a little vain when i did this one",
        "i cant walk into a shop anywhere where i do not feel uncomfortable",
        "i felt anger when at the end of a telephone call",
        "i like to have the same breathless feeling as a reader eager to see what will happen next",
        "i jest i feel grumpy tired and pre menstrual which i probably am but then again its only been a week and im about as fit as a walrus on vacation for the summer",
        "im feeling rather rotten so im not very ambitious right now",
        "i explain why i clung to a relationship with a boy who was in many ways immature and uncommitted despite the excitement i should have been feeling for getting accepted into the masters program at the university of virginia",
        "im updating my blog because i feel shitty",
        "i never make her separate from me because i don t ever want her to feel like i m ashamed with her",
        "i left with my bouquet of red and yellow tulips under my arm feeling slightly more optimistic than when i arrived",
        "i was feeling a little vain when i did this one",
        "i cant walk into a shop anywhere where i do not feel uncomfortable",
        "i felt anger when at the end of a telephone call",
        "i like to have the same breathless feeling as a reader eager to see what will happen next",
        "i jest i feel grumpy tired and pre menstrual which i probably am but then again its only been a week and im about as fit as a walrus on vacation for the summer",
    )

    override suspend fun fetchComments(quantity: Int): Result<List<CommentOutput>> {
        @Suppress("MagicNumber")
        delay(2000)
        return if (quantity > _comments.size) {
            Result.failure(IndexOutOfBoundsException())
        } else {
            Result.success(
                _comments.take(quantity).map { commentText ->
                    CommentOutput(
                        commentId = "whatever",
                        content = commentText
                    )
                },
            )
        }
    }
}
