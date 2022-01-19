package com.example.networking

import android.app.Application
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import java.util.logging.Logger


class RequestExecutorSecureV2Test : TestCase() {
    private val application = mockk<Application>()
    private val logger = mockk<Logger>()
    private val dependencies =  object: NetworkingSecureV2Dependencies{
        override val application: Application = this@RequestExecutorSecureV2Test.application
        override val logger: Logger = this@RequestExecutorSecureV2Test.logger
        override suspend fun getCertificate(): String {
            return "unused mock!"
        }
        override suspend fun saveToken(token: String) {}
        override suspend fun getToken(): String {
            return "unused mock!"
        }
    }

    fun testGet() = runBlocking{
        every { logger.info(any<String>()) } returns Unit
        val request = RequestExecutorSecureV2(dependencies)
        every { request["longToast"](any<CharSequence>()) } returns Unit
        val result = request.get()
        assertEquals("GET", result)
    }

    fun testPost() {}

    fun testPut() {}

    fun testDelete() {}
}