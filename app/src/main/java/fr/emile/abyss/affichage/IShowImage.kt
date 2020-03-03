package fr.emile.abyss.affichage

interface IShowImage {
    var imgId:Int

}

fun List<IShowImage>.getOnlyIdImage():List<Int>{
    val list= mutableListOf<Int>()
    this.forEach { list.add(it.imgId) }
    return list
}