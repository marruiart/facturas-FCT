package com.example.facturas_tfc.utils

import co.infinum.retromock.BodyFactory
import java.io.IOException
import java.io.InputStream

class ResourceBodyFactory : BodyFactory {

    @Throws(IOException::class)
    override fun create(input: String): InputStream {
        return App.context.assets.open(input)
    }

}