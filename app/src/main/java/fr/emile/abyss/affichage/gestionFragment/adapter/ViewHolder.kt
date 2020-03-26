package fr.emile.abyss.affichage.gestionFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.decodeSampledBitmapFromResource

abstract class ViewHolder<K:IShowImage> protected constructor(layoutInflated:View, private val activity: Context,
                                                              protected val reqHeight: Int,
                                                              protected val reqWidth: Int,
                                                              private val onclick:((view:View) -> Unit)

) : RecyclerView.ViewHolder(layoutInflated) {


    protected val img= itemView.findViewById<ImageView>(R.id.image_recycler_view)!!

    init {
        val param= RelativeLayout.LayoutParams(reqWidth,reqHeight)
        img.layoutParams=param

    }

    /**
     * fill view with something
     */
    fun initImageView(itemToShow:K)
    {
        img.setImageBitmap(decodeSampledBitmapFromResource(
                itemToShow.imgId,
                reqHeight,
                reqWidth,
                activity
            )
        )

        initPotentialView(itemToShow)

        img.setOnClickListener { onclick }

    }

    abstract fun initPotentialView(itemToShow:K)


}


fun createViewHolderImageOnly(parent: ViewGroup, activity: Context,
                              reqHeight: Int, reqWidth: Int,
                              onclick:((view:View) -> Unit)): ViewHolder<IShowImage> {


    return object : ViewHolder<IShowImage>(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_image_only, parent, false)
        , activity, reqHeight, reqWidth, onclick){
        override fun initPotentialView(itemToShow: IShowImage) {
            //no other view for now
        }
    }
}