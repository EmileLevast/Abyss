package fr.emile.abyss.affichage.gestionFragment.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.affichage.HEIGHT_SCREEN
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.WIDTH_SCREEN
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView


abstract class ImageAdapter<T:IShowImage>(
    val listImg: List<T>,
    private val activity: Context,
    ratioHeightScreenItem: Float,
    ratioWidthScreenItem: Float,
    private val recyclerView:HorizontalRecyclerView,
    private val factoryViewHolder:(parent: ViewGroup, activity: Context,
                                   reqHeight: Int, reqWidth: Int,
                                   onclick:ImageAdapter<T>)->ViewHolder<T>)

 : RecyclerView.Adapter<ViewHolder<T>>(),View.OnClickListener{

    private val reqHeightItem: Int= (HEIGHT_SCREEN!!*ratioHeightScreenItem).toInt()
    private val reqWidthItem: Int= (WIDTH_SCREEN!!*ratioWidthScreenItem).toInt()

    override fun getItemCount(): Int {
        return listImg.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return factoryViewHolder(parent,activity,reqHeightItem,reqWidthItem,this)//ViewHolder<T>(this, view)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.initImageView(listImg[position])
    }

    override fun onClick(v: View?) {
        onClickItem(recyclerView.getChildLayoutPosition(v!!))
    }

    //define what to do on click
    abstract fun onClickItem(position:Int)
}


