package com.example.exampgr208

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.image_rv_layout.view.*
import java.lang.System.load

class Adapter(val context: Context, val imageInfo:ImageInfo):
    RecyclerView.Adapter<Adapter.ViewHolderAdapter>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdapter {
        val view = LayoutInflater.from(context).inflate(R.layout.image_rv_layout, parent, false)
        return ViewHolderAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapter, position: Int) {


        //Glide.with(context.load(imageInfo.get(position).thumbnail_link).into(holder.imageResults))

        /*Glide.with(this)
            .load(postlink)
            .into(holder.resultImages)*/

    }

    class ViewHolderAdapter(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val recyclerView: Any
            get() {
                TODO()
            }
        val imageResults: ImageView = itemView.findViewById(R.id.iv_pick_image)
    }

    override fun getItemCount(): Int {
        return imageInfo.size
    }

}