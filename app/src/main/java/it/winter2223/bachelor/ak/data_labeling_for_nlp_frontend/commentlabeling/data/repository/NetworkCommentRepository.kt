package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.repository

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.model.Comment
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

class NetworkCommentRepository @Inject constructor() : CommentRepository {
    @Suppress("MaxLineLength")
    private val _comments = listOf(
        Comment(text = "im feeling rather rotten so im not very ambitious right now"),
        Comment(text = "i explain why i clung to a relationship with a boy who was in many ways immature and uncommitted despite the excitement i should have been feeling for getting accepted into the masters program at the university of virginia"),
        Comment(text = "im updating my blog because i feel shitty"),
        Comment(text = "i never make her separate from me because i don t ever want her to feel like i m ashamed with her"),
        Comment(text = "i left with my bouquet of red and yellow tulips under my arm feeling slightly more optimistic than when i arrived"),
        Comment(text = "i was feeling a little vain when i did this one"),
        Comment(text = "i cant walk into a shop anywhere where i do not feel uncomfortable"),
        Comment(text = "i felt anger when at the end of a telephone call"),
        Comment(text = "i like to have the same breathless feeling as a reader eager to see what will happen next"),
        Comment(text = "i jest i feel grumpy tired and pre menstrual which i probably am but then again its only been a week and im about as fit as a walrus on vacation for the summer"),
        Comment(text = "im feeling rather rotten so im not very ambitious right now"),
        Comment(text = "i explain why i clung to a relationship with a boy who was in many ways immature and uncommitted despite the excitement i should have been feeling for getting accepted into the masters program at the university of virginia"),
        Comment(text = "im updating my blog because i feel shitty"),
        Comment(text = "i never make her separate from me because i don t ever want her to feel like i m ashamed with her"),
        Comment(text = "i left with my bouquet of red and yellow tulips under my arm feeling slightly more optimistic than when i arrived"),
        Comment(text = "i was feeling a little vain when i did this one"),
        Comment(text = "i cant walk into a shop anywhere where i do not feel uncomfortable"),
        Comment(text = "i felt anger when at the end of a telephone call"),
        Comment(text = "i like to have the same breathless feeling as a reader eager to see what will happen next"),
        Comment(text = "i jest i feel grumpy tired and pre menstrual which i probably am but then again its only been a week and im about as fit as a walrus on vacation for the summer"),
    )

    override suspend fun fetchComments(quantity: Int): Result<List<Comment>> = coroutineScope {
        @Suppress("MagicNumber")
        delay(2000)
        if (quantity > _comments.size) {
            Result.failure(IndexOutOfBoundsException())
        } else {
            Result.success(_comments.take(quantity))
        }
    }

    override suspend fun postComments(comments: List<Comment>): Result<Unit> = coroutineScope {
        try {
            @Suppress("MagicNumber")
            delay(2000)
            comments.forEach { comment -> requireNotNull(comment.emotion) }
            Result.success(Unit)
        } catch (e: IllegalArgumentException) {
            Result.failure(e)
        }
    }
}
