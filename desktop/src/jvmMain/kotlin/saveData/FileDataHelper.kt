package saveData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.supervisorScope
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader


class FileDataHelper {
    companion object {
        suspend fun getContentAsync(file: String): Result = supervisorScope {
            async(Dispatchers.IO) {
                try {
                    val filePath = File(file).absolutePath
                    Result.Success(InputStreamReader(FileInputStream(filePath)).readText() )
                } catch (ex: Exception) {
                    Result.Error(ex.message.toString())
                }
            }.await()
        }

        suspend fun writeContentAsync(file: String, data: ByteArray, add: Boolean = false) = supervisorScope {
            async(Dispatchers.IO) {
                try {
                    if (isActive) {
                        val filePath = File(file).absolutePath
                        FileOutputStream(filePath, add).write(data)
                    } else return@async
                } catch (ex: Exception) {
                    throw Exception("@ ${ex.message}")
                }
            }.await()
        }
    }
}