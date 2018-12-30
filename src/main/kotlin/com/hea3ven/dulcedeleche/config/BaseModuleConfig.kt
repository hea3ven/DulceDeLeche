package com.hea3ven.dulcedeleche.config

abstract class BaseModuleConfig(val enabled: Boolean) {
    constructor() : this(true)
}