package hyt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputHandler {

    private static InputHandler singletonInstance = null;

    public List<String> lines;

    private InputHandler(String path) throws IOException {
        Stream<String> lineStream = Files.lines(Paths.get(path));
        lines = lineStream.collect(Collectors.toList());
        lineStream.close();
    }

    public static InputHandler getInputHandler(String path) throws IOException {
        singletonInstance = new InputHandler(path);
        return singletonInstance;
    }
}