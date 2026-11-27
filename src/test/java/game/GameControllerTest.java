package game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private ConsoleReader reader;
    @InjectMocks
    private GameController controller;

    @Test
    void shouldBeEqualNumberOfGamesAndRounds() throws IOException {
        when(reader.getNumberOfGames()).thenReturn(3);
        when(reader.getMoveFromPlayer()).thenReturn(2);

        controller.runApp();

        verify(reader, times(3)).suggestMove();
    }

    @Test
    void shouldReturnRockWhenPassedOne() {
        Move result = controller.findMove(1);

        assertThat(result).isEqualTo(Move.ROCK);
    }

    @Test
    void shouldReturnScissorsWhenPassedTwo() {
        Move result = controller.findMove(2);

        assertThat(result).isEqualTo(Move.SCISSORS);
    }

    @Test
    void shouldReturnPaperWhenPassedThree() {
        Move result = controller.findMove(3);

        assertThat(result).isEqualTo(Move.PAPER);
    }

    @Test
    void shouldThrowExceptionWhenMoreThanThreePassed() {
        assertThatThrownBy(() -> {
            controller.findMove(5);
        }).isInstanceOf(IllegalStateException.class).hasMessage("Unexpected move value");
    }

    @Test
    void shouldReturnDrawWhenMovesEqual() {
        assertThat(controller.defineWinner(getMove(Move.PAPER), getMove(Move.PAPER))).isEqualTo(Result.DRAW);
    }

    @Test
    void shouldReturnPlayerWins() {
        assertThat(controller.defineWinner(getMove(Move.ROCK), getMove(Move.SCISSORS))).isEqualTo(Result.PLAYER_WINS);
    }

    @Test
    void shouldReturnComputerWins() {
        assertThat(controller.defineWinner(getMove(Move.ROCK), getMove(Move.PAPER))).isEqualTo(Result.COMPUTER_WINS);
    }

    @Test
    void shouldIncrementPlayerScoreWhenPlayerWins() {
        controller.changeScore(Result.PLAYER_WINS);
        assertThat(controller.getPlayerScore()).isEqualTo(1);
        assertThat(controller.getComputerScore()).isEqualTo(0);
    }

    @Test
    void shouldIncrementComputerScoreWhenComputerWins() {
        controller.changeScore(Result.COMPUTER_WINS);

        assertThat(controller.getPlayerScore()).isEqualTo(0);
        assertThat(controller.getComputerScore()).isEqualTo(1);
    }

    @Test
    void shouldNotChangeScoreWhenDraw() {
        controller.changeScore(Result.DRAW);

        assertThat(controller.getPlayerScore()).isEqualTo(0);
        assertThat(controller.getComputerScore()).isEqualTo(0);
    }

    Move getMove(Move move) {
        return move;
    }
}
