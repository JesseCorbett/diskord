package com.jessecorbett.diskord.internal

import org.slf4j.LoggerFactory


actual class Logger actual constructor(name: String) {
    private val logger = LoggerFactory.getLogger(name)

    actual fun debug(message: String) {
        logger.debug(message)
    }

    actual fun info(message: String) {
        logger.info(message)
    }

    actual fun warn(message: String) {
        logger.warn(message)
    }

    actual fun error(message: String, error: Throwable?) {
        logger.error(message, error)
    }
}
