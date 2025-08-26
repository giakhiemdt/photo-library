package org.example.authservice.util;

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

fun Date.toLocalDateTime(): LocalDateTime =
    this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
