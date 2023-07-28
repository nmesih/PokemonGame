package service;

import model.Character;
import model.Pokemon;
import model.SpecialPower;

import java.util.ArrayList;

public class CharacterService {

    public Character createCharacter(String name, SpecialPower specialPower){
        Character character = new Character(name, specialPower);
        return character;
    }

    public void addPokemonToCharacter(Character character, Pokemon pokemon){
        if (character.getPokemonList() != null) {
            character.getPokemonList().add(pokemon);
        } else {
            ArrayList<Pokemon> pokemonArrayList = new ArrayList<>();
            pokemonArrayList.add(pokemon);
            character.setPokemonList(pokemonArrayList);
        }
    }

}
