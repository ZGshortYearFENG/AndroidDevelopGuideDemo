package com.example.opengl.utils

import android.opengl.GLES20

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
}