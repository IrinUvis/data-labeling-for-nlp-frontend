package it.nlp.frontend.data.remote.model.exception.messages

enum class SecurityExceptionMessage(val message: String) {
    InvalidEmailAddress("Email address is invalid"),
    InvalidPassword("Password must be at least 6 characters"),
    EmailAlreadyTaken("There already exists user with entered email"),
    BadCredentials("Bad credentials"),
    NoUserWithPassedId("There is no user with entered id: "),
    NoUserWithPassedEmail("User with entered email was not found"),
    TokenDoesNotExist("Entered token does not exist"),
    InvalidRefreshToken("Entered refresh token is invalid"),
}
