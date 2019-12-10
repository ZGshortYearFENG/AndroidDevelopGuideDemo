package fengzj.sample.androiddevelopguidedemo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { goToYome()}
    }

    // 通过scheme 隐式intent启动应用
    private fun goToYome(){
        Intent(Intent.ACTION_VIEW, Uri.parse("com.ypzdw.zdb://article")).also { startActivity(it) }
    }
}
