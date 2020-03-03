package fr.emile.abyss

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import fr.emile.abyss.modelClass.Player

open class Container<T>(list:List<T>)
{


    var listElt= list.toMutableList()
    var index=0
    protected set

    constructor(vararg itemList:T):this(itemList.toList())


    fun next(): T {
        up()

        return listElt[index]
    }

    fun previous(): T {

        down()

        return listElt[index]
    }

    fun up()
    {
        index++
        if(index>=listElt.size)index=0
    }

    fun down()
    {
        index--
        if(index<0)index=listElt.size-1
    }

    open fun reset()
    {
        index=0
    }

    fun add(itemToAdd: T)
    {
        listElt.add(itemToAdd)
    }

    fun getCurrent(): T {
        return listElt[index]
    }

    fun last(): T {
        return listElt.last()
    }

}

class ContainerPlayerExplo(list: List<Player>,var indexMainPlayer: Int): Container<Player>(list)
{
    init {
        reset()
    }
    fun isMainPlayer(): Boolean {
        return indexMainPlayer==index
    }

    fun getMainPlayer(): Player {
        return listElt[indexMainPlayer]
    }

    override fun reset() {
        index=indexMainPlayer
        up()
    }
}


/*****IMAGE****/
fun decodeSampledBitmapFromResource(
    resId: Int, reqWidth: Int, reqHeight: Int,context:Context): Bitmap {

    // First decode with inJustDecodeBounds=true to check dimensions
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(context.resources, resId, options)

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth,
        reqHeight)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeResource(context.resources, resId, options)
}

fun calculateInSampleSize(options: BitmapFactory.Options,
                          reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and
        // width
        val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
        val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

        // Choose the smallest ratio as inSampleSize value, this will
        // guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = if (heightRatio < widthRatio) widthRatio else heightRatio
    }

    return inSampleSize
}