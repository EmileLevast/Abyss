package fr.emile.abyss.affichage.gestionFragment.recyclerView

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.WIDTH_SCREEN
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter


class HorizontalRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr)
{

    private val padding=resources.getInteger(R.integer.width_stroke_recycler_view)
    init {
        addItemDecoration(MarginItemDecoration(padding))
        setBackgroundResource(R.drawable.background_recycler_view)
        setPadding(padding,0,padding,0)
    }



    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View,
                                    parent: RecyclerView, state: RecyclerView.State) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    left = spaceHeight
                }
                left =  spaceHeight
                right = spaceHeight
                bottom = spaceHeight
            }
        }
    }
}

