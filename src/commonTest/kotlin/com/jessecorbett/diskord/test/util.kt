package com.jessecorbett.diskord.test

/**
 * Run the specified block and wait for completion.
 */
expect fun <T> waitForTest(block: suspend () -> T): T
