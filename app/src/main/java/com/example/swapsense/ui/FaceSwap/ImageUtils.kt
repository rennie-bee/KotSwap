package com.example.swapsense.ui.FaceSwap

import android.graphics.*
import android.util.Log

class ImageUtils {

    companion object {

        private const val TAG = "ImageUtils"
        private const val MAX_IMAGE_SIZE = 1200

        /**
         * Resizes the input bitmap while maintaining aspect ratio.
         *
         * @param bm The bitmap to resize.
         * @return Scaled bitmap.
         */
        fun resizeBitmap(bm: Bitmap): Bitmap {
            Log.d(TAG, "resizeBitmap: Input width:${bm.width} height:${bm.height}")

            val height = bm.height
            val width = bm.width

            val ratioWidthHeight = width.toDouble() / height.toDouble()
            val ratioHeightWidth = height.toDouble() / width.toDouble()

            val newHeight = when {
                height < width -> (MAX_IMAGE_SIZE * ratioHeightWidth).toInt()
                else -> MAX_IMAGE_SIZE
            }
            val newWidth = when {
                height > width -> (MAX_IMAGE_SIZE * ratioWidthHeight).toInt()
                else -> MAX_IMAGE_SIZE
            }

            Log.d(TAG, "resizeBitmap: After resizing width:${newWidth} height:${newHeight}")
            return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true)
        }

        /**
         * Draws landmarks on a bitmap.
         *
         * @param bitmap The image to draw landmarks on.
         * @param landmarks Landmarks to draw.
         * @return Bitmap with landmarks as circles.
         */
        fun drawLandmarksOnBitmap(
            bitmap: Bitmap, landmarks: ArrayList<ArrayList<PointF>>
        ): Bitmap? {
            val bitmapWithLandmarks = bitmap.copy(bitmap.config, true)
            val canvas = Canvas(bitmapWithLandmarks)

            for (i in 0 until landmarks.size) {
                for (j in 0 until landmarks[i].size) {
                    val cx = landmarks[i][j].x
                    val cy = landmarks[i][j].y
                    val paint = Paint()
                    paint.style = Paint.Style.FILL_AND_STROKE
                    paint.color = Color.GREEN
                    canvas.drawCircle(cx, cy, 10F, paint)
                }
            }
            return bitmapWithLandmarks
        }
    }

}
