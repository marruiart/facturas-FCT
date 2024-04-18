package com.example.facturas_tfc.data.network.invoicesApi

import co.infinum.retromock.BodyFactory
import com.example.facturas_tfc.di.App
import java.io.IOException
import java.io.InputStream

class ResourceBodyFactory : BodyFactory {

    @Throws(IOException::class)
    override fun create(input: String): InputStream {
        return App.context.assets.open(input)
    }

}