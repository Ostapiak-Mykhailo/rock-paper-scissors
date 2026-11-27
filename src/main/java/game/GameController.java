package game;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Getter
public class GameController {

    private final ConsoleReader consoleReader;
    private String userName;
    private int round = 1;
    private int playerScore;
    private int computerScore;

    public GameController(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    public void runApp() throws IOException {
        userName = consoleReader.getUserName();
        int gamesNumber = consoleReader.getNumberOfGames();
        log.info("Game started. Player {}, Games {}", userName, gamesNumber);
        while (round <= gamesNumber) {
            consoleReader.suggestMove();
            int playerNumber = consoleReader.getMoveFromPlayer();
            int computerNumber = getMoveFromComputer();
            Move playerMove = findMove(playerNumber);
            Move computerMove = findMove(computerNumber);
            Result result = defineWinner(playerMove, computerMove);
            if (!result.equals(Result.DRAW)) {
                changeScore(result);
            }
            printRoundResult(playerMove, computerMove, result);
            round++;
        }
        printGameResult();
    }

    void changeScore(Result result) {
        if (result.equals(Result.PLAYER_WINS)) {
            playerScore++;
        } else if (result.equals(Result.COMPUTER_WINS)) {
            computerScore++;
        }
    }

    private int getMoveFromComputer() {
        return (int) (Math.random() * 3) + 1;
    }

    Move findMove(int move) {
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

    Result defineWinner(Move playerMove, Move computerMove) {
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
        log.info("Round {} finished. Your move: {}; Computer move: {}", round, playerMove, computerMove);
        if (result.equals(Result.PLAYER_WINS)) {
            log.info("You have won the round");
        } else if (result.equals(Result.COMPUTER_WINS)) {
            log.info("Computer has won the round");
        } else {
            log.info("It`s a draw. The account has not changed.");
        }
        log.info("""
                The current score is:
                {} - {}\s
                Computer - {}""", userName, playerScore, computerScore);
    }

    private void printGameResult() {
        if (playerScore == computerScore) {
            log.info("""
                    It`s a draw with final score:
                    Final score:
                    {} - {}\s
                    Computer - {}
                    """, userName, playerScore, computerScore);
        } else if (playerScore > computerScore) {
            log.info("""
                    Congratulations you have won the game
                    Final score:
                    {} - {}\s
                    Computer - {}
                    """, userName, playerScore, computerScore);
        } else {
            log.info("""
                    Sorry you have lost.
                    Final score:
                    {} - {}\s
                    Computer - {}
                    """, userName, playerScore, computerScore);
        }
    }
}
