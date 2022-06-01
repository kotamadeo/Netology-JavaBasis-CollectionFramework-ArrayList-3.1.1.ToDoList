package calculator;

import com.gmail.at.kotamadeo.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Верные выражения.csv", numLinesToSkip = 1, delimiter = '=')
    void correctExpressions(String input, String expected) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(null);
        assertEquals(expected, out.toString().stripTrailing());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/inputMismatch.csv")
    void inputMismatch(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThrows(InputMismatchException.class, () -> {
            Main.main(null);
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/noSuchElement.csv")
    void noSuchElement(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThrows(NoSuchElementException.class, () -> {
            Main.main(null);
        });
    }
}