package com.jessecorbett.diskord.test

/**
 * Run the specified block and wait for completion.
 */
internal expect fun <T> waitForTest(block: suspend () -> T): T
