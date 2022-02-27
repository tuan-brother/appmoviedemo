package com.example.mydemoproject.utils

import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import android.text.Spanned

import android.text.SpannableStringBuilder

import android.text.style.RelativeSizeSpan


fun View.setMarginStartAndEnd(@DimenRes marginResLeft: Int, @DimenRes marginResRight: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        context.resources.getDimensionPixelSize(marginResLeft).also {
            marginStart = it
        }
        context.resources.getDimensionPixelSize(marginResRight).also {
            marginEnd = it
        }
    }
}

fun SpannableStringBuilder(
    text: String,
    afterChar: Char,
    reduceBy: Float
): SpannableStringBuilder {
    val smallSizeText = RelativeSizeSpan(reduceBy)
    val ssBuilder = SpannableStringBuilder(text)
    ssBuilder.setSpan(
        smallSizeText,
        text.indexOf(afterChar),
        text.length,
        Spanned.SPAN_USER_SHIFT
    )
    return ssBuilder
}