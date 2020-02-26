package org.echowiki.core.expression;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class UnitTestSimpleExpressionEngine {

    SimpleWikiExpressionEngine engine = new SimpleWikiExpressionEngine();

    @Test
    public void unitTestEncodingParagraphExpectScopeExpression() {
        String INPUT_TEXT = getInputFromFile("wiki_expression/wiki-paragraph1.test");
        String EXPECT_TEXT = getInputFromFile("wiki_expression/wiki-encoded-paragraph1.test");

        Paragraph paragraph = engine.encodingParagraph(INPUT_TEXT);
        assertEquals(paragraph.rawString(), INPUT_TEXT);
        assertEquals(paragraph.encodedString(), EXPECT_TEXT);

        INPUT_TEXT = getInputFromFile("wiki_expression/wiki-paragraph2.test");
        EXPECT_TEXT = getInputFromFile("wiki_expression/wiki-encoded-paragraph2.test");
        paragraph = engine.encodingParagraph(INPUT_TEXT);
        assertEquals(paragraph.rawString(), INPUT_TEXT);
        assertEquals(paragraph.encodedString(), EXPECT_TEXT);
    }

    @Test
    public void unitTestEncoding() {
        String INPUT_TEXT = getInputFromFile("wiki_expression/wiki-document1.test");
        String EXPECT_TEXT = getInputFromFile("wiki_expression/wiki-encoded-document1.test");
        List<Paragraph> paragraphs = engine.encoding(INPUT_TEXT);
        List<String> encodedString = paragraphs.stream().map(Paragraph::encodedString).collect(Collectors.toList());
        assertEquals(String.join("\n", encodedString), EXPECT_TEXT);
    }

    private String getInputFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(fileName)))) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
                if (scanner.hasNextLine())
                    sb.append("\n");
            }
        }
        return sb.toString();
    }
}
