data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val role: String
)

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)

data class ProfileResponse(
    val username: String,
    val email: String,
)