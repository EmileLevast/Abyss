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

    final override fun onClick(v: View?) {
        onClick(listImg,recyclerView.getChildLayoutPosition(v!!))
    }

    //do not call super in overriding function otherwise you will call twice onClickItem if you are not careful
    open fun onClick(listItem:List<T>,indexClicked:Int)
    {
        onClickItem(indexClicked)
    }

    //define what to do on click
    //TODO delete this and use [onCick(List<T>, Int)] instead. Just have to rework some adapter in frag. Nothing big
    abstract fun onClickItem(position:Int)
}


