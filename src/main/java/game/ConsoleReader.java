package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readConsole() throws IOException {
        return reader.readLine();
    }

    public void suggestMove() {
        System.out.println("""
                Please make your move:
                Enter 1 to choose Rock
                Enter 2 to choose Scissors
                Enter 3 to choose Paper""");
    }
}
