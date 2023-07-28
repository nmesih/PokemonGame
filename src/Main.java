import model.*;
import model.Character;
import service.GameService;
import service.LoadService;
import service.PlayerService;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        LoadService loadService = new LoadService();
        PlayerService playerService = new PlayerService();
        GameService gameService = new GameService();

        Scanner input = new Scanner(System.in);

        ArrayList<Character> characterList = loadService.loadCharacters();
        ArrayList<Pokemon> pokemonList = loadService.loadPokemons();

        /*
        Oyuncuları oluşturmak için karakter ve pokemon seçtirildi, oyuncu ismi girildi.
         */

        System.out.println("Pokemon oyununa hoş geldiniz");
        System.out.println("Player 1 için karakter seçin");
        int charCounter = 1;
        for (Character character:characterList
        ) {
            System.out.println(charCounter +"-"+ character.getName());
            charCounter++;
        };
        int player1Choice = input.nextInt();
        System.out.println("Player 2 için karakter seçin");
        int charCounter2 = 1;
        for (Character character:characterList
        ) {
            System.out.println(charCounter2 +"-"+ character.getName());
            charCounter2++;
        };
        int player2Choice = input.nextInt();

        System.out.println("Player 1 için pokemon seçin");
        int pokemonCount = 1;
        for (Pokemon pokemon : pokemonList) {
            System.out.println(pokemonCount+"-"+pokemon.getName());
            pokemonCount++;
        }
        int pokemon1Choice = input.nextInt();
        characterList.get(player1Choice-1).getPokemonList().add(pokemonList.get(pokemon1Choice-1));
        pokemonList.remove(pokemonList.get(pokemon1Choice-1));

        System.out.println("Player 2 için pokemon seçin");
        int pokemonCount2 = 1;
        for (Pokemon pokemon : pokemonList) {
            System.out.println(pokemonCount2+"-"+pokemon.getName());
            pokemonCount2++;
        }
        int pokemon2Choice = input.nextInt();

        characterList.get(player2Choice-1).getPokemonList().add(pokemonList.get(pokemon2Choice-1));
        input.nextLine();

        System.out.print("Player 1'in adını girin : ");
        String username1=input.nextLine();
        Player player1 = playerService.createPlayer(username1,characterList.get(player1Choice-1));

        System.out.print("Player 2'nin adını girin : ");
        String username2=input.nextLine();
        Player player2 = playerService.createPlayer(username2,characterList.get(player2Choice-1));



        //Random classı yardımıyla oyuna başlayacak oyuncu seçiliyor.
        Random rand = new Random();
        int randomStarter = rand.nextInt(3);
        if (randomStarter == 0){
            System.out.println(player1.getName()+" oyuna başlayacak.");
        }else {
            System.out.println(player2.getName()+" oyuna başlayacak");
        }


        for (int i=0;i<2;i++){
            System.out.println("*************************");
            System.out.println("Round "+ (i+1) +" başlıyor!");
            System.out.println("*************************");
            while((player1.getCharacter().getPokemonList().size() > 1 ||
                    player2.getCharacter().getPokemonList().size() > 1 ||
                    (player1.getCharacter().getPokemonList().get(0).getHealth()>0
                            && player2.getCharacter().getPokemonList().get(0).getHealth()>0))) {

                if (randomStarter == 0 || randomStarter > 2) {

                    if (player1.getCharacter().getPokemonList().get(0).getHealth() > 0 ||
                            player1.getCharacter().getPokemonList().size() > 1) {

                        if (player1.getCharacter().getPokemonList().get(0).getHealth() <= 0 &&
                                player1.getCharacter().getPokemonList().size() > 1) {
                            player1.getCharacter().getPokemonList().remove(0);
                        }

                        System.out.println(player1.getName() + " saldırı türünü seç");
                        System.out.println("1- Normal saldırı\n2- Pokemon special saldırı\n3- Karakter Special saldırı\n" +
                                "4- Pokemon & Karakter Special Saldırı");
                        int player1AttackChoice = input.nextInt();

                        switch (player1AttackChoice) {
                            case 1 -> {
                                gameService.attack(player1, player2, false, false);
                                gameService.healthCheck(player2);
                            }
                            case 2 -> {
                                gameService.attack(player1, player2, true, false);
                                gameService.healthCheck(player2);
                            }
                            case 3 -> {
                                gameService.attack(player1, player2, false, true);
                                gameService.healthCheck(player2);
                            }
                            case 4 -> {
                                gameService.attack(player1, player2, true, true);
                                gameService.healthCheck(player2);
                            }
                        }
                    }
                    randomStarter = 3;
                }
                if (randomStarter == 1 || randomStarter > 2) {

                    if (player2.getCharacter().getPokemonList().get(0).getHealth() > 0 ||
                            player2.getCharacter().getPokemonList().size() > 1) {

                        if (player2.getCharacter().getPokemonList().get(0).getHealth() <= 0 &&
                                player2.getCharacter().getPokemonList().size() > 1) {
                            player2.getCharacter().getPokemonList().remove(0);
                        }

                        System.out.println(player2.getName() + " saldırı türünü seç");
                        System.out.println("1- Normal saldırı\n2- Pokemon special saldırı\n3- Karakter Special saldırı\n" +
                                "4- Pokemon & Karakter Special Saldırı");
                        int player2AttackChoice = input.nextInt();


                        switch (player2AttackChoice) {
                            case 1 -> {
                                gameService.attack(player2, player1, false, false);
                                gameService.healthCheck(player1);
                            }
                            case 2 -> {
                                gameService.attack(player2, player1, true, false);
                                gameService.healthCheck(player1);
                            }
                            case 3 -> {
                                gameService.attack(player2, player1, false, true);
                                gameService.healthCheck(player1);
                            }
                            case 4 -> {
                                gameService.attack(player2, player1, true, true);
                                gameService.healthCheck(player1);
                            }
                        }
                    }
                    randomStarter = 3;
                }
            }
            gameService.findWinner(player1,player2);
        }
        System.out.println("Oyun bitti!");
 }
}