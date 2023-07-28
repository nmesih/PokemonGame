package service;

import model.Player;
import model.Pokemon;
import model.WeatherEnum;

import java.util.Random;

public class WeatherService {

    public WeatherEnum randomWeather() {
        Random rand = new Random();
        int intRand = rand.nextInt(3);
        return switch (intRand) {
            case 0 -> WeatherEnum.SUNNY;
            case 1 -> WeatherEnum.RAINY;
            case 2 -> WeatherEnum.STORMY;
            default -> null;
        };
    }

    public int updateAttackByWeather(Player player, WeatherEnum weatherEnum) {

        Pokemon pokemon = player.getCharacter().getPokemonList().get(0);
        int damage = 0;
        switch (weatherEnum) {
            case SUNNY:
                System.out.println("Hava güneşli");
                if(pokemon.getType().toString().equals("FIRE")){
                    System.out.println(pokemon.getName()+" 'ın verdiği hasar +3 arttı.");
                    damage = 3;
                }else if (pokemon.getType().toString().equals("WATER")){
                    System.out.println(pokemon.getName()+" 'ın verdiği hasar -3 azaldı.");
                    damage = -3;
                }else {
                    System.out.println("Mevuct hava koşulu " + pokemon.getName() + "'i etkilemiyor");
                }
                break;
            case RAINY:
                System.out.println("Weather is rainy");
                if(pokemon.getType().toString().equals("WATER")){
                    System.out.println(pokemon.getName()+" 'ın verdiği hasar +3 arttı.");
                    damage = 3;
                }else if (pokemon.getType().toString().equals("FIRE")){
                    System.out.println(pokemon.getName()+" 'ın verdiği hasar -3 azaldı.");
                    damage = -3;
                }else {
                    System.out.println("Mevuct hava koşulu " + pokemon.getName() + "'i etkilemiyor");
                }
            case STORMY:
                System.out.println("Weather is stormy");
                if(pokemon.getType().toString().equals("ELECTRICITY")){
                    System.out.println(pokemon.getName()+" 'ın verdiği hasar +3 arttı.");
                    damage = 3;
                }else if (pokemon.getType().toString().equals("EARTH")){
                    System.out.println(pokemon.getName()+" 'ın verdiği hasar -3 azaldı.");
                    damage = -3;
                }else {
                    System.out.println("Mevuct hava koşulu " + pokemon.getName() + "'i etkilemiyor");
                }
                break;

        }
        return damage;
    }
}
