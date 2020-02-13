package fr.emile.abyss.modelClass.gameItems

class Lord (var FishType: FishType, var name:String, var imgId:Int, var price:Int,
            var numberAllieType:Int, var obligedType:FishType) {

    override fun toString(): String {
        return "Lord(FishType=$FishType, name='$name', imgId=$imgId, price=$price, numberAllieType=$numberAllieType, obligedType=$obligedType)"
    }
}