package fr.emile.abyss

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