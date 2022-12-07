package it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.impl

import com.google.common.truth.Truth
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.mockk.coEvery
import io.mockk.mockk
import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkAuthenticationRepositoryTest {

    @Test
    fun repository_calledLogInWithResponseBeingCorrect_returnsSuccessWithUserOutput() = runTest {
        val authToken = "authToken"
        val refreshToken = "refreshToken"
        val userId = "userId"
        val email = "email"
        val password = "password"
        val expiresIn = "expiresIn"
        val userInput = UserInput(
            email = email,
            password = password,
        )
        val userOutput = UserOutput(
            authToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
            email = email,
            expiresIn = expiresIn,
        )

        val httpClientMock: HttpClient = mockk()
        val responseMock: HttpResponse = mockk()

        coEvery {
            httpClientMock.post(any<String>(), any())
        } returns responseMock
        coEvery { responseMock.body<UserOutput>() } returns userOutput

        val repository = NetworkAuthenticationRepository(
            httpClient = httpClientMock,
        )

        val expectedResult = DataResult.Success(userOutput)

        val result = repository.logIn(userInput)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

//    @Test
//    fun test() = runTest {
//        val httpClientMock: HttpClient = mockk()
//        val responseMock: HttpResponse = mockk()
//        val userOutput = UserOutput()
//
//        coEvery { httpClientMock.post(any<String>(), any()) } returns responseMock
//        coEvery { responseMock.body<UserOutput>() } returns userOutput
//    }
}