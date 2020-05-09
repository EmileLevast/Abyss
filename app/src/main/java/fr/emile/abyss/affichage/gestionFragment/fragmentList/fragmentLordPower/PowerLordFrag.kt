package fr.emile.abyss.affichage.gestionFragment.fragmentList.fragmentLordPower

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.ViewHolder
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderImageOnly
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.Location

open class PowerLordFrag<T:IShowImage>(
    val listToShow: List<T>,
    private val explicationPower:String,
    private val resourceIdBackground:Int,
    private val factoryViewHolder:(parent: ViewGroup, activity: Context,
                                   reqHeight: Int, reqWidth: Int,
                                   onclick:ImageAdapter<T>)->ViewHolder<T>,
    val actionOnClick:(listItem:List<T>,indexClicked:Int)->Unit): CustomFragment<Player>(){

    private lateinit var recyclerViewImage: HorizontalRecyclerView
    private lateinit var adapterImage: ImageAdapter<T>
    private lateinit var textExplanationPower:TextView
    private lateinit var imageViewBackground:ImageView

    override val idLayoutToInflate= R.layout.frag_layout_power_lord


    //just to let the power lord with Location override it
    open val orientationRecyclerView:Int=RecyclerView.HORIZONTAL
    open val ratioHeightImageInsideRecyclerView:Float=0.5f
    open val ratioWidthImageInsideRecyclerView:Float=0.15f


    override fun createView(viewInflated: View) {

        recyclerViewImage=viewInflated.findViewById(R.id.recyclerViewPowerLordFrag)
        textExplanationPower=viewInflated.findViewById(R.id.textViewExplanationPowerLordFrag)
        textExplanationPower.text=explicationPower
        imageViewBackground=viewInflated.findViewById(R.id.imageViewFragPowerLordBackground)
        imageViewBackground.setImageResource(resourceIdBackground)

        recyclerViewImage.layoutManager= LinearLayoutManager(activity, orientationRecyclerView,false)

        adapterImage= object : ImageAdapter<T>(listToShow,activity!!,ratioHeightImageInsideRecyclerView,ratioWidthImageInsideRecyclerView,recyclerViewImage,factoryViewHolder){

            override fun onClick(listItem: List<T>, indexClicked: Int) {
                actionOnClick(listItem,indexClicked)
                notifyDataSetChanged()
            }

            //useless because it isn't call as I redefined OnClick just above
            override fun onClickItem(position: Int) =Unit
        }

        recyclerViewImage.adapter=adapterImage
    }

    override fun updateView(dataGame: Player) {
        adapterImage.notifyDataSetChanged()
    }
}

class PowerLordFragLocation(
    listToShow: List<Location>,
    explicationPower:String,
    resourceIdBackground:Int,
    actionOnClick:(listItem:List<Location>,indexClicked:Int)->Unit
):PowerLordFrag<Location>(listToShow,explicationPower,resourceIdBackground,::createViewHolderImageOnly,actionOnClick)
{
    override val orientationRecyclerView: Int=RecyclerView.VERTICAL
    override val ratioHeightImageInsideRecyclerView: Float=0.25f
    override val ratioWidthImageInsideRecyclerView: Float=1f
}