package com.example.coursesapp.presentation.login

import android.text.InputFilter
import android.text.Spanned

class CyrillicInputFilter : InputFilter {
    private val cyrillicRegex = "[а-яА-ЯёЁ]".toRegex()

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        source?.let {
            for (i in start until end) {
                if (cyrillicRegex.matches(source[i].toString())) {
                    return ""
                }
            }
        }

        return null
    }
}