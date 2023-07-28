package service;

import model.Player;
import model.Pokemon;
import model.WeatherEnum;

import java.util.ArrayList;

public class GameService {

    LoadService loadService = new LoadService();
    ArrayList<Pokemon> pokemonList = loadService.loadPokemons();
    WeatherService weatherService = new WeatherService();

    public void attack(Player attacker, Player defender, boolean isPokeSpecialAttack, boolean isCharSpecialAttack) {
        Pokemon attackingPokemon = attacker.getCharacter().getPokemonList().get(0);
        Pokemon defendingPokemon = defender.getCharacter().getPokemonList().get(0);

        boolean specialAttack = false;
        if (isPokeSpecialAttack && isCharSpecialAttack) {
            specialAttack = attackingPokemon.getSpecialPower().getRemainingRights() > 0
                    && attacker.getCharacter().getSpecialPower().getRemainingRights() > 0;
        } else if (isPokeSpecialAttack) {
            specialAttack = attackingPokemon.getSpecialPower().getRemainingRights() > 0;
        } else if (isCharSpecialAttack) {
            specialAttack = attacker.getCharacter().getSpecialPower().getRemainingRights() > 0;
        }

        WeatherEnum weather = weatherService.randomWeather();
        int damage = weatherService.updateAttackByWeather(attacker, weather);
        int charRemainingRights = attacker.getCharacter().getSpecialPower().getRemainingRights();

        if (specialAttack) {
            if (isPokeSpecialAttack && isCharSpecialAttack) {
                damage += attackingPokemon.specialAttack();
                damage += attacker.getCharacter().getSpecialPower().getExtraDamage();
                attacker.getCharacter().getSpecialPower().setRemainingRights(charRemainingRights - 1);
                System.out.println(attacker.getName() + "'nin pokemonu " + attackingPokemon.getName() + " "+ damage + " hasar verdi ");
            } else if (isPokeSpecialAttack) {
                damage += attackingPokemon.specialAttack();
                System.out.println(attacker.getName() + "'nin pokemonu " + attackingPokemon.getName() + " "+ damage + " hasar verdi ");
            } else {
                damage += attackingPokemon.getDamage();
                damage += attacker.getCharacter().getSpecialPower().getExtraDamage();
                attacker.getCharacter().getSpecialPower().setRemainingRights(charRemainingRights - 1);
                System.out.println(attacker.getName() + "'nin pokemonu " + attackingPokemon.getName() + " "+ damage + " hasar verdi ");
            }
        } else {
            if (isPokeSpecialAttack || isCharSpecialAttack) {
                System.out.println(attacker.getName() + "'nin pokemonu " + attackingPokemon.getName() + " "+ 0 + " hasar verdi ");
                System.out.println("Özel gücün kalmadı");

            } else {
                damage += attackingPokemon.getDamage();
                System.out.println(attacker.getName() + "'nin pokemonu " + attackingPokemon.getName() + " "+ damage + " hasar verdi ");
            }
        }

        defendingPokemon.setHealth(defendingPokemon.getHealth() - damage);
        System.out.println(defender.getName()+"'nin pokemonu " + defendingPokemon.getName()+ "'nin " + defendingPokemon.getHealth() + " canı kaldı.");
    }

    public void healthCheck(Player player){
        if (player.getCharacter().getPokemonList().get(0).getHealth() > 0) {
            System.out.println(player.toString());
            System.out.println("Oyun devam ediyor.");
        } else {
            System.out.println(player.toString());
            System.out.println(player.getName() + "'nin pokemonu öldü.");
        }
    }

    public void addLowestDamagePokemon(Player player){

        Pokemon lowestDamage = new Pokemon();

        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getDamage() < lowestDamage.getDamage()){
            lowestDamage = pokemon;
        }
        }
        player.getCharacter().getPokemonList().add(lowestDamage);
    }

    /*
     Birinci raundu kaybeden oyuncuya pokemon listesinden saldırı gücü en düşük pokemon eklendi.
     Birinci raundun kazananına kaybedenin pokemonu eklendi.
     */
    public void findWinner(Player player1, Player player2){

        if (player1.getCharacter().getPokemonList().get(0).getHealth() <= 0){

            player2.getCharacter().getPokemonList().add(player1.getCharacter().getPokemonList().get(0));
            player2.getCharacter().getPokemonList().get(1).setHealth(100);

            addLowestDamagePokemon(player1);
            player1.getCharacter().getPokemonList().remove(0);
        }else {
            player1.getCharacter().getPokemonList().add(player2.getCharacter().getPokemonList().get(0));
            player1.getCharacter().getPokemonList().get(1).setHealth(100);

            addLowestDamagePokemon(player2);
            player2.getCharacter().getPokemonList().remove(0);
        }
    }

}
