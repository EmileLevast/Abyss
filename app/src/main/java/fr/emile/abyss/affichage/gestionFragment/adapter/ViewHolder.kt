package fr.emile.abyss.affichage.gestionFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.ActivePermanentPower
import fr.emile.abyss.modelClass.gameItems.Ally
import fr.emile.abyss.modelClass.gameItems.Lord

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
        /*img.setImageBitmap(decodeSampledBitmapFromResource(
                itemToShow.imgId,
                reqHeight,
                reqWidth,
                activity
            )
        )*/

        img.setImageResource(itemToShow.imgId)

        itemView.setOnClickListener (listener)

        initPotentialView(itemToShow)


    }

    abstract fun initPotentialView(itemToShow:K)


}


fun <T:IShowImage> createViewHolderImageOnly(parent: ViewGroup, activity: Context,
                              reqHeight: Int, reqWidth: Int,
                              listener:ImageAdapter<T>): ViewHolder<T> {


    return object : ViewHolder<T>(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_image_only, parent, false)
        , activity, reqHeight, reqWidth, listener){
        override fun initPotentialView(itemToShow: T) {
            //no other view for now
        }
    }
}

fun createViewHolderLord(parent: ViewGroup, activity: Context,
                                             reqHeight: Int, reqWidth: Int,
                                             listener:ImageAdapter<Lord>): ViewHolder<Lord> {


    return object : ViewHolder<Lord>(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_lord, parent, false)
        , activity, reqHeight, reqWidth, listener){

        private val imageStatusLord:ImageView=itemView.findViewById(R.id.imageview_status_lord)

        override fun initPotentialView(itemToShow: Lord) {
            if(!itemToShow.isAlive)
            {
                imageStatusLord.setImageResource(R.drawable.skull_sea)
            }
            else if(!itemToShow.isFree)
            {
                imageStatusLord.setImageResource(R.drawable.symbole_cle)
            }
            else if(itemToShow.power is ActivePermanentPower && itemToShow.power.isAvailable)
            {
                imageStatusLord.setImageResource(0)
                imageStatusLord.setBackgroundResource(R.drawable.background_active_permanent_power)
            }
            else
            {
                imageStatusLord.visibility=View.GONE
            }
        }
    }
}


fun createViewHolderAllyCheckBox(parent: ViewGroup, activity: Context,
                                 reqHeight: Int, reqWidth: Int,
                                 listener: ImageAdapter<Ally>): ViewHolder<Ally> {


    return object : ViewHolder<Ally>(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_ally, parent, false)
        , activity, reqHeight, reqWidth, listener){

        //view added in the recycler view
        private val textViewValueAlly: TextView =itemView.findViewById(R.id.textValueAllyRecyclerView)
        private val checkBoxBuyWithAlly: CheckBox =itemView.findViewById(R.id.checkboxBuyWithAllyRecyclerView)

        override fun initPotentialView(itemToShow: Ally) {
            textViewValueAlly.text = itemToShow.number.toString()
            checkBoxBuyWithAlly.isChecked=itemToShow.selectedToBuyLord
        }
    }
}

fun createViewHolderPlayer(parent: ViewGroup, activity: Context,
                           reqHeight: Int, reqWidth: Int,
                           listener: ImageAdapter<Player>): ViewHolder<Player> {


    return object : ViewHolder<Player>(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_image_legend, parent, false)
        , activity, reqHeight, reqWidth, listener){

        //view added in the recycler view
        //we use the same layout as for the ally card because we need a text to print the name
        private val textViewLegendForImage: TextView =itemView.findViewById(R.id.textLegendImageRecyclerView)

        override fun initPotentialView(itemToShow: Player) {
            textViewLegendForImage.text = itemToShow.nom
        }
    }
}

fun createViewHolderAlly(parent: ViewGroup, activity: Context,
                           reqHeight: Int, reqWidth: Int,
                           listener: ImageAdapter<Ally>): ViewHolder<Ally> {


    return object : ViewHolder<Ally>(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_image_legend, parent, false)
        , activity, reqHeight, reqWidth, listener){

        //view added in the recycler view
        //we use the same layout as for the ally card because we need a text to print the name
        private val textViewLegendForImage: TextView =itemView.findViewById(R.id.textLegendImageRecyclerView)

        override fun initPotentialView(itemToShow: Ally) {
            textViewLegendForImage.text = itemToShow.number.toString()
        }
    }
}