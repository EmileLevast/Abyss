package fr.emile.abyss.affichage.gestionFragment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.affichage.HEIGHT_SCREEN
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.WIDTH_SCREEN
import fr.emile.abyss.controller
import fr.emile.abyss.decodeSampledBitmapFromResource


class ImageAdapter(
    private val listImg: List<IShowImage>,
    private val activity: Context,
    ratioHeightScreen: Float,
    ratioWidthScreen: Float
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private val reqHeight: Int= (HEIGHT_SCREEN!!*ratioHeightScreen).toInt()
    private val reqWidth: Int= (WIDTH_SCREEN!!*ratioWidthScreen).toInt()

    override fun getItemCount(): Int {
        return listImg.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_image_only, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setImage(listImg[position].imgId)

        holder.img.setOnClickListener { Log.w("msg","click recyclerView") }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val img= view.findViewById<ImageView>(R.id.image_recycler_view)!!
        init {
            val param= LinearLayout.LayoutParams(reqWidth,reqHeight)
            img.layoutParams=param
        }

        fun setImage(resId:Int)
        {
            img.setImageBitmap(decodeSampledBitmapFromResource(resId,reqHeight,reqWidth, activity))
        }
    }
}
