package fr.emile.abyss.modelClass.gameItems

// represent the token that you win when you beat a monster
class MonsterToken (var typeMonsterToken: TypeMonsterToken)

enum class TypeMonsterToken{
    KEY,
    PERL_2,
    PERL_3,
    COUNCIL_STACK
}