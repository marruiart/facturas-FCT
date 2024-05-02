package com.marinaruiz.facturas_fct.data.network.firebase.model

import com.marinaruiz.facturas_fct.core.ErrorResponse

data class SignUpResult(
    val error: ErrorResponse?,
    val allow: Boolean
)