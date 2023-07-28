package service;

import model.Character;
import model.Player;
import model.Pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayerService {

    LoadService loadService = new LoadService();
    GameService gameService = new GameService();
    Scanner input = new Scanner(System.in);

    public Player createPlayer(String name, Character character){
        return new Player(name, character);
    }

    public Player createPlayerWithInputFromUser(LoadService loadService, ArrayList<Pokemon> pokemonList , Scanner input){
        System.out.print("Player'ın ismini giriniz: ");
        String name = input.next();

        System.out.println("Player için karakter seçiniz(Ash için 1'e, Brock için 2'ye basınız)");
        int ch = input.nextInt();

        Character character = loadService.loadCharacters().get(ch-1);
        Player player = createPlayer(name, character);

        System.out.println("Player için pokemon seçiniz: ");
        int count = 1;
        for (Pokemon pokemon: pokemonList) {
            System.out.println(count + " - "+ pokemon.getName());
            count++;
        }

        int choose = input.nextInt();
        Pokemon pokemon = pokemonList.get(choose - 1);
        ArrayList<Pokemon> playerPokemonList = new ArrayList<>();
        playerPokemonList.add(pokemon);
        player.getCharacter().setPokemonList(playerPokemonList);
        return player;
    }
}
