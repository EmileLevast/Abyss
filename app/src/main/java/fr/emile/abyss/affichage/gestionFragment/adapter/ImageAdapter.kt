package fr.emile.abyss.affichage.gestionFragment.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.affichage.HEIGHT_SCREEN
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.WIDTH_SCREEN


class ImageAdapter<T:IShowImage>(
    val listImg: List<T>,
    private val activity: Context,
    ratioHeightScreenItem: Float,
    ratioWidthScreenItem: Float,
    private val factoryViewHolder:(parent: ViewGroup, activity: Context,
                                   reqHeight: Int, reqWidth: Int,
                                   onclick:((view:View) -> Unit))->ViewHolder<T>,
    private val onclickItem:((view: View) -> Unit))

 : RecyclerView.Adapter<ViewHolder<T>>() {

    private val reqHeightItem: Int= (HEIGHT_SCREEN!!*ratioHeightScreenItem).toInt()
    private val reqWidthItem: Int= (WIDTH_SCREEN!!*ratioWidthScreenItem).toInt()

    override fun getItemCount(): Int {
        return listImg.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return factoryViewHolder(parent,activity,reqHeightItem,reqWidthItem,onclickItem)//ViewHolder<T>(this, view)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.initImageView(listImg[position])
}

}
