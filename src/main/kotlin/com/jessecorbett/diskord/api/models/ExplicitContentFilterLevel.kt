package com.jessecorbett.diskord.api.models

enum class ExplicitContentFilterLevel(val level: Int) {
    DISABLED(0),
    MEMBERS_WITHOUT_ROLES(1),
    ALL_MEMBERS(2)
}
