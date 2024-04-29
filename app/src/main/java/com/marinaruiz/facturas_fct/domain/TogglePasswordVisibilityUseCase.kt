package com.marinaruiz.facturas_fct.domain

import android.graphics.drawable.Icon
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.CompoundButtonCompat.setButtonTintList
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.di.App.Companion.context


class TogglePasswordVisibilityUseCase {

    operator fun invoke(
        toggle: CheckBox, isChecked: Boolean, vararg editTexts: EditText
    ) {
        val eyeIcon = Icon.createWithResource(context, R.drawable.ic_eye)
        val eyeOffIcon = Icon.createWithResource(context, R.drawable.ic_eye_off)
        if (isChecked) {
            toggle.setButtonIcon(eyeOffIcon)
            val colorStateList =
                ContextCompat.getColorStateList(context, R.color.black)
            setButtonTintList(toggle, colorStateList)
            editTexts.forEach { editText ->
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        } else {
            toggle.setButtonIcon(eyeIcon)
            editTexts.forEach { editText ->
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }
}
