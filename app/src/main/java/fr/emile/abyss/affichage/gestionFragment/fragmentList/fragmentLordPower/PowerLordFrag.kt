package fr.emile.abyss.affichage.gestionFragment.fragmentList.fragmentLordPower

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.ViewHolder
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.modelClass.Player

class PowerLordFrag<T:IShowImage>(
    val listToShow: List<T>,
    val explicationPower:String,
    val resourceIdBackground:Int,
    private val factoryViewHolder:(parent: ViewGroup, activity: Context,
                                   reqHeight: Int, reqWidth: Int,
                                   onclick:ImageAdapter<T>)->ViewHolder<T>,
    val actionAfterClick:(T)->Unit): CustomFragment<Player>(){

    private lateinit var recyclerViewImage: HorizontalRecyclerView
    private lateinit var adapterImage: ImageAdapter<T>
    private lateinit var textExplanationPower:TextView
    private lateinit var imageViewBackground:ImageView

    override val idLayoutToInflate= R.layout.frag_layout_power_lord


    override fun createView(viewInflated: View) {

        recyclerViewImage=viewInflated.findViewById(R.id.recyclerViewPowerLordFrag)
        textExplanationPower=viewInflated.findViewById(R.id.textViewExplanationPowerLordFrag)
        textExplanationPower.text=explicationPower
        imageViewBackground=viewInflated.findViewById(R.id.imageViewFragPowerLordBackground)
        imageViewBackground.setImageResource(resourceIdBackground)

        recyclerViewImage.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

        adapterImage= object : ImageAdapter<T>(listToShow,activity!!,0.5f,0.15f,recyclerViewImage,factoryViewHolder){
            override fun onClickItem(position: Int) {
                actionAfterClick(listImg[position])
            }
        }

        recyclerViewImage.adapter=adapterImage
    }

    override fun updateView(dataGame: Player) {
        adapterImage.notifyDataSetChanged()
    }
}