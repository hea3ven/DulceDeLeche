package com.hea3ven.util

import org.apache.logging.log4j.LogManager
import java.lang.reflect.Field

fun reflectField(klass: Class<*>, deobfName: String, obfName: String, fieldConsumer: (Field) -> Unit) {
    val field = try {
        klass.getDeclaredField(obfName)
    } catch (e: NoSuchFieldException) {
        klass.getDeclaredField(deobfName)
    }
    try {
        field.isAccessible = true
        fieldConsumer(field)
    } finally {

        field.isAccessible = false
    }
}
