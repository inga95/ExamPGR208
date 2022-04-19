package com.example.exampgr208


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.results.*


class MainActivity : AppCompatActivity() {

    private lateinit var searchBtn: Button
    private val dataList: MutableList<ImageApi> = mutableListOf()
    private lateinit var adapter: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = Adapter(dataList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        recyclerView.adapter = adapter

        searchBtn = findViewById(R.id.bt_search)

        searchBtn.setOnClickListener {
            AndroidNetworking.initialize(this)

            AndroidNetworking.get("http://api-edu.gtl.ai/api/v1/imagesearch/bing")
                .addQueryParameter(
                    "url",
                    "https://w1.pngwing.com/pngs/370/239/png-transparent-chinese-beagle-chinese-crested-dog-puppy-maltese-dog-pet-sitting-bark-dog-walking.png"
                )
                .build()
                .getAsObject(ImageInfo::class.java, object : ParsedRequestListener<ImageInfo> {
                    override fun onResponse(response: ImageInfo) {
                        dataList.addAll(response)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onError(anError: ANError?) {
                    }
                })
        }

        //Gjør at man kan åpne kamera i emulatoren/mobilen og ta bilde
        takePictureBtn.setOnClickListener{
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
        }

        var iv_pick_image: ImageView? = null
        var mGetContent: ActivityResultLauncher<String?>? = null
        iv_pick_image = findViewById(R.id.iv_pick_image)
        //iv_pick_image.bringToFront()
        iv_pick_image.setOnClickListener(View.OnClickListener {
            Log.d("== My activity ===", "OnClick is called")
            mGetContent!!.launch("image/*")
        })
        mGetContent = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { result ->
            val intent = Intent(this@MainActivity, Cropper::class.java)
            intent.putExtra("DATA", result.toString())
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == -1 && requestCode == 101) {
            val result = data!!.getStringExtra("RESULT")
            var resultUri: Uri? = null
            if (result != null) {
                resultUri = Uri.parse(result)
            }
            var iv_pick_image: ImageView? = null
            iv_pick_image = findViewById(R.id.iv_pick_image)
            iv_pick_image!!.setImageURI(resultUri)
        }else if (requestCode == 123){
            var pic = data?.getParcelableExtra<Bitmap>("data")
            iv_pick_image.setImageBitmap(pic)
        }
    }
}