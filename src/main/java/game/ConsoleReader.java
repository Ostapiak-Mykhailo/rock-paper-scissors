package game;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class ConsoleReader {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String getInput() throws IOException {
        return reader.readLine();
    }

    public void suggestMove() {
        System.out.println("""
                Please make your move:
                Enter 1 to choose Rock
                Enter 2 to choose Scissors
                Enter 3 to choose Paper""");
    }

    String getUserName() throws IOException {
        System.out.println("Please enter your name");
        return getInput();
    }

    int getNumberOfGames() throws IOException {
        System.out.println("How many times would you like to play?");
        int gamesNumber = -1;
        while (gamesNumber <= 0) {
            String str = getInput();
            try {
                gamesNumber = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                log.warn("Invalid input: {} Please enter only a number", str);
                continue;
            }
            if (gamesNumber <= 0) {
                log.warn("Invalid number of games: {} Please enter a positive number", gamesNumber);
            }
        }
        return gamesNumber;
    }

    int getMoveFromPlayer() throws IOException {
        int move = -1;
        while (move < 1 || move > 3) {
            String input = getInput();
            try {
                move = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                log.warn("Invalid input: {} Please enter only a number", move);
                continue;
            }
            if (move < 1 || move > 3) {
                log.warn("No such option provided. Please try again");
            }
        }
        return move;
    }
}
