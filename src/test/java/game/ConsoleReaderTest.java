package game;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleReaderTest {

    @Test
    void shouldReturnPositiveNumberWhenPositiveNumberPassed() throws IOException {

        System.setIn(new ByteArrayInputStream("-1\n0\n3\n".getBytes()));

        ConsoleReader reader = new ConsoleReader();
        int numberOfGames = reader.getNumberOfGames();

        assertThat(numberOfGames).isEqualTo(3);
    }

    @Test
    void getNumberOfGamesShouldNotFailWhenLettersPassed() throws IOException {
        System.setIn(new ByteArrayInputStream("abc\n1\n".getBytes()));

        ConsoleReader reader = new ConsoleReader();
        int result = reader.getNumberOfGames();

        assertThat(result).isEqualTo(1);
    }

    @Test
    void shouldReturnEnteredUserName() throws IOException {
        System.setIn(new ByteArrayInputStream("User\n".getBytes()));

        ConsoleReader reader = new ConsoleReader();
        String userName = reader.getUserName();

        assertThat(userName).isEqualTo("User");
    }

    @Test
    void getMoveFromPlayerShouldReturnPassedNumberWithinExpectedRange() throws IOException {
        System.setIn(new ByteArrayInputStream("-16\n20\n1\n".getBytes()));

        ConsoleReader reader = new ConsoleReader();
        int playerMove = reader.getMoveFromPlayer();

        assertThat(playerMove).isEqualTo(1);
    }

    @Test
    void getMoveFromPlayerShouldNotFailWhenLettersPassed() throws IOException {
        System.setIn(new ByteArrayInputStream("abc\n1\n".getBytes()));

        ConsoleReader reader = new ConsoleReader();
        int result = reader.getMoveFromPlayer();

        assertThat(result).isEqualTo(1);
    }
}
