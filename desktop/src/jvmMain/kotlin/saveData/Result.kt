package saveData

sealed class Result {
    class Success (val data: String) : Result()
    class Error (val error: String): Result()
}