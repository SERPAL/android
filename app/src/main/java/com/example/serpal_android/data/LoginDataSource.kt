package com.example.serpal_android.data

import com.example.serpal_android.data.model.LoggedInUser
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.wait
import java.io.IOException
import java.util.concurrent.CompletableFuture

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    var client = OkHttpClient()
    var success = false
    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            if(run(username, password).get()){
                val user = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
                return Result.Success(user)
            }else{
                return Result.Error(IOException("Error logging in"))
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    fun run(username: String, password: String): CompletableFuture<Boolean> {
        val postBody = "{\"username\":\"$username\", \"password\":\"$password\"}"
        println(postBody)

        val request = Request.Builder()
            .url("http://10.0.2.2:8000/api/token/")
            .post(postBody.toRequestBody(JSON))
            .build()
        var future : CompletableFuture<Boolean> = CompletableFuture()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                future.complete(false)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) future.complete(false)

                    future.complete(true)
                }
            }
        })
        return future
    }
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
}