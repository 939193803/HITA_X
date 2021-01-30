package com.stupidtree.hita.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.text.TextUtils.isEmpty
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.NotificationTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.stupidtree.hita.R
import java.util.*

/**
 * 此类封装了加载用户头像的各个方法
 * 以及各种图形函数
 */
object ImageUtils {

    fun loadAvatarInto(context: Context, filename: String?, target: ImageView) {
        if (isEmpty(filename)) {
            target.setImageResource(R.drawable.ic_baseline_location_24)
        } else {
            val glideUrl = GlideUrl("http://hita.store:3000/user/profile/avatar?path=" +
                    filename, LazyHeaders.Builder().addHeader("device-type", "android").build())
            Glide.with(context).load(glideUrl
            ).apply(RequestOptions.bitmapTransform(CircleCrop())).placeholder(R.drawable.ic_baseline_location_24).into(target)
        }
    }
    /**
     * convert dp to its equivalent px
     *
     * 将dp转换为与之相等的px
     */
    fun dp2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}