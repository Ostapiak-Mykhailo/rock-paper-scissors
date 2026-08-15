package game;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class GameController {

    private final ConsoleReader consoleReader = new ConsoleReader();
    private String userName;
    private int gamesNumber;
    private int round = 1;
    private int playerScore;
    private int computerScore;

    public void runApp() throws IOException {
        userName = getUserName();
        gamesNumber = getNumberOfGames();
        log.info("Game started. Player {}, Games {}", userName, gamesNumber);
        while (round <= gamesNumber) {
            consoleReader.suggestMove();
            Move playerMove = getMoveFromPlayer();
            Move computerMove = getMoveFromComputer();
            Result result = defineWinner(playerMove, computerMove);
            if (result.equals(Result.PLAYER_WINS)) {
                playerScore++;
            } else if (result.equals(Result.COMPUTER_WINS)) {
                computerScore++;
            }
            printRoundResult(playerMove, computerMove, result);
            log.info("Round {} finished. Player move: {}; Computer move: {}; result: {}",
                    round, playerMove, computerMove, result);
            round++;
        }
        printGameResult();
        log.info("Game finished with final score: \n" +
                "Player - {}\n" +
                "Computer - {}", playerScore, computerScore);
    }

    private int getNumberOfGames() throws IOException {
        System.out.println("How many times would you like to play?");
        int gamesNumber = -1;
        while (gamesNumber <= 0) {
            String str = consoleReader.readConsole();
            try {
                gamesNumber = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                System.out.println("Please enter only a number");
                log.warn("Invalid input: {}", str);
                continue;
            }
            if (gamesNumber <= 0) {
                System.out.println("Please enter a positive number");
                log.warn("Invalid number of games: {}", gamesNumber);
            }
        }
        return gamesNumber;
    }

    private String getUserName() throws IOException {
        System.out.println("Please enter your name");
        return consoleReader.readConsole();
    }

    private Move getMoveFromPlayer() throws IOException {
        int move = -1;
        while (move < 1 || move > 3) {
            String input = consoleReader.readConsole();
            try {
                move = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }
            if (move < 1 || move > 3) {
                System.out.println("No such option provided. Please try again");
            }
        }
        return getMove(move);
    }

    private Move getMoveFromComputer() {
        int move = (int) (Math.random() * 3) + 1;
        return getMove(move);
    }

    private Move getMove(int move) {
        switch (move) {
            case 1 -> {
                return Move.ROCK;
            }
            case 2 -> {
                return Move.SCISSORS;
            }
            case 3 -> {
                return Move.PAPER;
            }
            default -> throw new IllegalStateException("Unexpected move value");
        }
    }

    private Result defineWinner(Move playerMove, Move computerMove) {
        if (playerMove.equals(computerMove)) {
            return Result.DRAW;
        } else if (playerMove.equals(Move.ROCK) && computerMove.equals(Move.SCISSORS) ||
                playerMove.equals(Move.SCISSORS) && computerMove.equals(Move.PAPER) ||
                playerMove.equals(Move.PAPER) && computerMove.equals(Move.ROCK)) {
            return Result.PLAYER_WINS;
        } else {
            return Result.COMPUTER_WINS;
        }
    }

    private void printRoundResult(Move playerMove, Move computerMove, Result result) {
        System.out.println("Your move: " + playerMove + "\n" +
                "Computer move: " + computerMove);
        if (result.equals(Result.PLAYER_WINS)) {
            System.out.println("You have won the round");
        } else if (result.equals(Result.COMPUTER_WINS)) {
            System.out.println("Computer has won the round");
        } else {
            System.out.println("It`s a draw. The account has not changed.");
        }
        System.out.println("The current score is:\n" +
                userName + " - " + playerScore + "\n" +
                "Computer - " + computerScore);
    }

    private void printGameResult() {
        if (playerScore == computerScore) {
            System.out.println("It`s a draw with final score:\n" +
                    userName + " - " + playerScore + "\n" +
                    "Computer - " + computerScore);
        } else if (playerScore > computerScore) {
            System.out.println("Congratulations you have won the game\n" +
                    "Final score:\n" +
                    userName + " - " + playerScore + "\n" +
                    "Computer - " + computerScore);
        } else {
            System.out.println("Sorry you have lost.\n" +
                    "Final score:\n" +
                    userName + " - " + playerScore + "\n" +
                    "Computer - " + computerScore);
        }
    }
}
