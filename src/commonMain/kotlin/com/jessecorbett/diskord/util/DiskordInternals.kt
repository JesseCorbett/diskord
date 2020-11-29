package com.jessecorbett.diskord.util

/**
 * Denotes an internal Diskord API.
 *
 * These APIs are not guaranteed to remain stable between any release and
 * should not be directly consumed. Use at your own risk.
 */
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
public annotation class DiskordInternals
