package fr.emile.abyss.modelClass.gameItems

class Lord (var FishType: FishType, var name:String, var imgId:Int, var price:Int,
            var numberAllieType:Int, var obligedType:FishType, var influencePoint:Int) {

    override fun toString(): String {
        return "Lord(FishType=$FishType, name='$name', influence Point='$influencePoint', imgId=$imgId, price=$price, numberAllieType=$numberAllieType, obligedType=$obligedType)"
    }
}