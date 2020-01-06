package com.example.opengl.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils

object OpenglUtil {

    // 动态编译源码返回引用id
    fun compileShaderCode(type: Int, shaderCode: String): Int {

        val shaderObjectId = GLES20.glCreateShader(type)
        if (shaderObjectId != 0) {
            GLES20.glShaderSource(shaderObjectId, shaderCode)
            GLES20.glCompileShader(shaderObjectId)

            // 检测在调用glCompileShader后编译是否成功了
            val status = intArrayOf(0)
            GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, status, 0)
            if (status[0] == 0) {
                // 编译失败，清除创建的着色器对象
                GLES20.glDeleteShader(shaderObjectId)
                return 0
            }
        }
        return shaderObjectId
    }

    fun createTexture(context: Context, id: Int): Int {

        val options = BitmapFactory.Options()
        options.inScaled = false
        val mBitmap = BitmapFactory.decodeResource(context.resources, id, options)
        //保存到textureObjectId
        val textureObjectId = IntArray(1)
        if (mBitmap != null && !mBitmap.isRecycled) {
            //生成一个纹理，保存到这个数组中
            GLES20.glGenTextures(1, textureObjectId, 0)
            //绑定GL_TEXTURE_2D
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectId[0])
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST.toFloat()
            )
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_NEAREST.toFloat()
            )
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE.toFloat()
            )
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE.toFloat()
            )
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0)
            //回收释放
            mBitmap.recycle()
            //因为我们已经复制成功了。所以就进行解除绑定。防止修改
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
            return textureObjectId[0]
        }
        return 0
    }
}