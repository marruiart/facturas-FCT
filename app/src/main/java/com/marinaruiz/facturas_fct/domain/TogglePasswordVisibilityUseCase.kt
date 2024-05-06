package com.marinaruiz.facturas_fct.domain

import android.graphics.drawable.Icon
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.widget.CheckBox
import android.widget.EditText
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.di.App.Companion.context


object ToggleState {
    val stateMap = mapOf(
        true to mapOf(
            "eyeIcon" to Icon.createWithResource(context, R.drawable.ic_eye_off),
            "transformation" to HideReturnsTransformationMethod.getInstance()
        ), false to mapOf(
            "eyeIcon" to Icon.createWithResource(context, R.drawable.ic_eye),
            "transformation" to PasswordTransformationMethod.getInstance()
        )
    )
}

class TogglePasswordVisibilityUseCase {

    operator fun invoke(
        toggle: CheckBox, isChecked: Boolean, vararg editTexts: EditText
    ) {
        val state = checkNotNull(ToggleState.stateMap[isChecked])
        toggle.setButtonIcon(state["eyeIcon"] as Icon)
        editTexts.forEach { editText ->
            editText.transformationMethod = state["transformation"] as TransformationMethod
        }
    }
}
