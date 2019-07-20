package com.jessecorbett.diskord.test

import kotlinx.coroutines.runBlocking

actual fun <T> waitForTest(block: suspend () -> T): T = runBlocking { block() }
