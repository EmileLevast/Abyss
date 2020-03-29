package fr.emile.abyss.affichage.gestionFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.decodeSampledBitmapFromResource
import fr.emile.abyss.modelClass.gameItems.Ally

abstract class ViewHolder<K:IShowImage> protected constructor(layoutInflated:View, private val activity: Context,
                                                              protected val reqHeight: Int,
                                                              protected val reqWidth: Int,
                                                              private val listener:ImageAdapter<K>

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

        itemView.setOnClickListener (listener)

    }

    abstract fun initPotentialView(itemToShow:K)


}


fun createViewHolderImageOnly(parent: ViewGroup, activity: Context,
                              reqHeight: Int, reqWidth: Int,
                              listener:ImageAdapter<IShowImage>): ViewHolder<IShowImage> {


    return object : ViewHolder<IShowImage>(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_image_only, parent, false)
        , activity, reqHeight, reqWidth, listener){
        override fun initPotentialView(itemToShow: IShowImage) {
            //no other view for now
        }
    }
}

fun createViewHolderAlly(parent: ViewGroup, activity: Context,
                         reqHeight: Int, reqWidth: Int,
                         listener: ImageAdapter<Ally>
): ViewHolder<Ally> {


    return object : ViewHolder<Ally>(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_ally, parent, false)
        , activity, reqHeight, reqWidth, listener){

        //view added in the recycler view
        private val textViewValueAlly: TextView =itemView.findViewById(R.id.textValueAllyRecyclerView)

        override fun initPotentialView(itemToShow: Ally) {
            textViewValueAlly.text = itemToShow.number.toString()
        }
    }
}