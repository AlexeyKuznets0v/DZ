package tests;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class InputCapture {
    private InputStream originalIn = System.in;

    public void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    public void restore() {
        System.setIn(originalIn);
    }
}