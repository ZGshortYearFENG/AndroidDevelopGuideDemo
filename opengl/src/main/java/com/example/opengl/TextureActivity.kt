package com.example.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import com.example.opengl.utils.OpenglUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.properties.Delegates

class TextureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextureSurfaceView(this))
    }
}

class TextureSurfaceView(context: Context, attributeSet: AttributeSet? = null) :
    GLSurfaceView(context) {

    private val mVertexCode = "attribute vec4 aPosition;\n" +
            "attribute vec2 aTexturePosition;\n" +
            "varying vec2 vTexturePosition;\n" +
            "void main(){\n" +
            "    gl_Position = aPosition;\n" +
            "    vTexturePosition=aTexturePosition;\n" +
            "}"

    private val mColorCode = "precision mediump float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "varying vec2 vTexturePosition;\n" +
            "void main(){\n" +
            "    gl_FragColor =  texture2D(uTextureUnit, vTexturePosition);\n" +
            "}"

    /*
    顶点数据
    OpenGL不是简单地把所有的3D坐标变换为屏幕上的2D像素；
    OpenGL仅当3D坐标在3个轴（x、y和z）上都为-1.0到1.0的范围内时才处理它。
    所有在所谓的标准化设备坐标(Normalized Device Coordinates)范围内的坐标才会最终呈现在屏幕上（在这个范围以外的坐标都不会显示）。
    标准化设备坐标接着会变换为屏幕空间坐标(Screen-space Coordinates)，这是使用你通过glViewport函数提供的数据，进行视口变换(Viewport Transform)完成的。所得的屏幕空间坐标又会被变换为片段输入到片段着色器中。

    下面仅设置了x，y，还有一个z坐标可以理解成深度，它代表一个像素在空间中和你的距离，如果离你远就可能被别的像素遮挡，你就看不到它了，它会被丢弃，以节省资源。
    */
    private val mVertex = floatArrayOf(
        -0.5f, -0.5f,
        -0.5f, 0.5f,
        0.5f, -0.5f,
        0.5f, 0.5f
    )

    private val mTexture = floatArrayOf(
        0f, 1f,
        0f, 0f,
        1f, 1f,
        1f, 0f
    )

    // 可以通过顶点缓冲对象(Vertex Buffer Objects, VBO)管理这个内存，它会在GPU内存(通常被称为显存)中储存大量顶点。
    // 使用这些缓冲对象的好处是我们可以一次性的发送一大批数据到显卡上，而不是每个顶点发送一次。

    private var mVertexBuffer: FloatBuffer
    private var mTextureBuffer: FloatBuffer
    private var mProgramObjectId by Delegates.notNull<Int>()
    private var mTextureId: Int? = null

    init {
        // 环境设置
        setEGLContextClientVersion(2)

        mVertexBuffer =
            ByteBuffer.allocateDirect(4 * 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
                .put(mVertex)
        mTextureBuffer =
            ByteBuffer.allocateDirect(4 * 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
                .put(mTexture)
        mVertexBuffer.position(0)
        mTextureBuffer.position(0)
        setRenderer(object : Renderer {
            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                // 着色器程序对象(Shader Program Object)是多个着色器合并之后并最终链接完成的版本
                mProgramObjectId = GLES20.glCreateProgram()
                val vertexShaderObjectId =
                    OpenglUtil.compileShaderCode(GLES20.GL_VERTEX_SHADER, mVertexCode)
                val fragmentShaderObjectId =
                    OpenglUtil.compileShaderCode(GLES20.GL_FRAGMENT_SHADER, mColorCode)
                GLES20.glAttachShader(mProgramObjectId, vertexShaderObjectId)
                GLES20.glAttachShader(mProgramObjectId, fragmentShaderObjectId)
                GLES20.glLinkProgram(mProgramObjectId)

                mTextureId = OpenglUtil.createTexture(context, R.drawable.place_holder_live_room)
            }

            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                // 告诉OpenGL渲染窗口的尺寸大小,glViewport函数来设置窗口的维度
                // OpenGL幕后使用glViewport中定义的位置和宽高进行2D坐标的转换，将OpenGL中的位置坐标转换为你的屏幕坐标。例如，OpenGL中的坐标(-0.5, 0.5)有可能（最终）被映射为屏幕中的坐标(200,450)
                GLES20.glViewport(0, 0, width, height)
            }

            override fun onDrawFrame(gl: GL10?) {

                // glClearColor,设置清空屏幕所用的颜色,清除颜色缓冲之后，整个颜色缓冲都会被填充为glClearColor里所设置的颜色
                GLES20.glClearColor(1f, 1f, 1f, 1f)
                // glClear函数,清空屏幕的颜色缓冲,它接受一个缓冲位(Buffer Bit)来指定要清空的缓冲
                // 只关心颜色值，所以我们只清空颜色缓冲GLES20.GL_COLOR_BUFFER_BIT
                // glClearColor函数是一个状态设置函数，而glClear函数则是一个状态应用的函数。所以需要先设置缓冲后应用
                GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT)
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

                // 首先要激活程序对象
                GLES20.glUseProgram(mProgramObjectId)
                // 将顶点数据链接到顶点着色器的属性上
                val aPosition = GLES20.glGetAttribLocation(mProgramObjectId, "aPosition")
                GLES20.glEnableVertexAttribArray(aPosition)
                GLES20.glVertexAttribPointer(
                    aPosition, //指定我们要配置的顶点属性
                    2, //指定顶点属性的大小
                    GLES20.GL_FLOAT, //指定数据的类型
                    false, //是否希望数据被标准化
                    2 * 4, //连续的顶点属性组之间的间隔
                    mVertexBuffer
                )

                val aTexturePosition =
                    GLES20.glGetAttribLocation(mProgramObjectId, "aTexturePosition")
                GLES20.glEnableVertexAttribArray(aTexturePosition)
                GLES20.glVertexAttribPointer(
                    aTexturePosition,
                    2,
                    GLES20.GL_FLOAT,
                    false,
                    2 * 4,
                    mTextureBuffer
                )

                // 激活GL_TEXTURE0区域，方便后面关联buffer数据到sampler2D uTextureUnit上
                // 他们之间的逻辑关系是
                // GLES20.glUniform1i(GLES20.glGetUniformLocation(mProgramObjectId, "uTextureUnit"), 0) 让纹理 0 和 sampler2D uTextureUnit相关联
                // glActiveTexture(GLES20.GL_TEXTURE0) glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId!!); 让纹理 0 和 mTextureId相关联
                // 所以sampler2D就能跟mTextureId关联
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0) // 理论上可以不用这一句,因为sampler2D默认值为0，纹理也默认与GL_TEXTURE0关联
                // glBindTexture告诉opengl说，我选择tex_id作为当前纹理，后续对纹理的操作都将作用在此纹理上
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId!!);
                GLES20.glUniform1i(GLES20.glGetUniformLocation(mProgramObjectId, "uTextureUnit"), 0)


                //绘制三角形.
                //draw arrays的几种方式 GL_TRIANGLES三角形 GL_TRIANGLE_STRIP三角形带的方式(开始的3个点描述一个三角形，后面每多一个点，多一个三角形) GL_TRIANGLE_FAN扇形(可以描述圆形)
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
            }
        })
    }
}