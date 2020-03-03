/*
package fr.emile.abyss.affichage.gestionFragment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.decodeSampledBitmapFromResource


class ImageAdapter(
    private val context: Context,
    private val listImg: List<IShowImage>,
    private val reqHeight: Int,
    private val reqWidth: Int
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return listImg.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imgView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_image_only, parent, false).
            findViewById<ImageView>(R.id.image_recycler_view)
        return ViewHolder(imgView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setImage(listImg[position].imgId,context)

        holder.img.setOnClickListener { Log.w("msg","click recyclerView") }
    }

    inner class ViewHolder(val img: ImageView) : RecyclerView.ViewHolder(img) {


        init {
            val param= LinearLayout.LayoutParams(reqWidth,reqHeight)
            img.layoutParams=param
        }

        fun setImage(resId:Int,context: Context)
        {
            img.setImageBitmap(decodeSampledBitmapFromResource(resId,reqHeight,reqWidth,context))
        }
    }
}
*/
