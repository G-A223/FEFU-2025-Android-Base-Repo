package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.isGone

class FlexBoxLayout @JvmOverloads constructor (
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        var lineWidth = 0
        var lineHeight = 0
        var height = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (child.isGone) continue

            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (lineWidth + childWidth > widthSize) {
                lineWidth = childWidth
                lineHeight += height
                height = childHeight
            } else {
                lineWidth += childWidth
                height = maxOf(height, childHeight)
            }

        }

        setMeasuredDimension(widthSize, lineHeight + height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var x = 0
        var y = 0
        var lineHeight = 0
        val lineWidth = r - l

        for (i in 0..< childCount) {
            val child = getChildAt(i)

            if (child.isGone) continue

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (x + childWidth > lineWidth) {
                x = left
                y += lineHeight
                lineHeight = 0
            }

            child.layout(x, y, x + childWidth, y + childHeight)

            x += childWidth
            lineHeight = maxOf(lineHeight, childHeight)
        }


    }
}