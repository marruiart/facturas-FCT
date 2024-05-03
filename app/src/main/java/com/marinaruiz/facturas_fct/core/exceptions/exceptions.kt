package com.marinaruiz.facturas_fct.core.exceptions

open class RemoteConfigException(message: String) : Exception(message)
class ErrorOnSetDefaultAsyncException : RemoteConfigException("Set defaults failed")