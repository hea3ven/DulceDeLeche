package com.hea3ven.util

import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import net.fabricmc.loader.FabricLoader
import java.lang.reflect.Type
import java.nio.file.Files

fun <T> readConfig(configFileName: String, configClass: Class<T>, defaultConfigBuilder: () -> T,
        vararg configBuilders: Pair<Type, () -> Any>): T {

    val configDir = FabricLoader.INSTANCE.configDirectory.toPath()
    val gson = GsonBuilder().setPrettyPrinting().apply {
        configBuilders.forEach { builderDesc ->
            registerTypeAdapter(builderDesc.first, InstanceCreator { builderDesc.second() })
        }
    }.create();
    val configFile = configDir.resolve("$configFileName.json")
    if (Files.exists(configFile)) {
        Files.newBufferedReader(configFile).use {
            return gson.fromJson(it, configClass)
        }
    } else {
        val cfg = defaultConfigBuilder()
        Files.createDirectories(configFile.parent)
        Files.newBufferedWriter(configFile).use {
            gson.toJson(cfg, it)
        }
        return cfg
    }
}
