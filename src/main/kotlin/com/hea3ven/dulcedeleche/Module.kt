package com.hea3ven.dulcedeleche

import com.hea3ven.dulcedeleche.config.BaseModuleConfig
import org.apache.logging.log4j.LogManager

abstract class Module<T : BaseModuleConfig> {

    protected val logger = LogManager.getFormatterLogger("ModDulceDeLeche." + this.javaClass.simpleName);

    lateinit var cfg: T
    abstract fun createDefaultConfig(): T
    abstract fun onInitialize()
}