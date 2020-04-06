package hyt;

import java.io.IOException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        try {
            InputHandler inputHandler = InputHandler.getInputHandler("regexParser/input.txt"); 
            assert inputHandler.lines.size() == 2 : "bad input: expected 2 lines";
            Parser parser = new Parser(inputHandler.lines.get(0));
            NFA nfa = parser.parse();
            boolean result = nfa.matches(inputHandler.lines.get(1));
            System.out.println("Regex: " +  inputHandler.lines.get(0));
            System.out.println("String: " + inputHandler.lines.get(1));
            System.out.println("Parse result: " + result);
        } catch (IOException e) {
            throw new RuntimeException("error reading file");
        }
    }
}
