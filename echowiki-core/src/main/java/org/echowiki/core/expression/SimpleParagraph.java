package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;

import java.util.*;

import static com.google.common.base.Preconditions.*;
import static org.echowiki.core.expression.StringHelper.NEW_LINE;

public class SimpleParagraph implements Paragraph {

    private static final String EXPRESSION_SYMBOL = "[@echo%d]";
    private static final String PARAGRAPH_SYMBOL = "[@paragraph]";
    private final String[] rawLines;
    private final String[] encodedLines;
    private final ScopeExpression scope;
    private final Element rootElement;
    private final Map<String, Expression> expressions;
    private final Map<String, Element> elements;

    public SimpleParagraph(String[] rawLines,
                           String[] encodedLines,
                           ScopeExpression scopeExpression,
                           Map<String, Expression> mapper) {
        checkArgument(rawLines.length == encodedLines.length);
        checkArgument(scopeExpression != null);
        this.rawLines = rawLines.clone();
        this.encodedLines = encodedLines.clone();
        this.scope = scopeExpression;
        this.rootElement = scopeExpression.evaluate();
        this.expressions = new LinkedHashMap<>(mapper);
        elements = new LinkedHashMap<>();
        for (String key : expressions.keySet()) {
            Expression expression = expressions.get(key);
            elements.put(key, expression.evaluate());
        }
    }

    @Override
    public Element root() {
        return rootElement;
    }

    @Override
    public List<Element> getElements() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public String encodedString() {
        return concatLines(encodedLines);
    }

    @Override
    public String rawString() {
        return concatLines(rawLines);
    }

    private String concatLines(String[] lines) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<lines.length; i++) {
            sb.append(lines[i]);
            if (i != lines.length-1)
                sb.append(NEW_LINE);
        }
        return sb.toString();
    }

    @Override
    public String[] encodedLines() {
        return encodedLines.clone();
    }

    @Override
    public Element indexAt(int index) {
        checkElementIndex(elements.size(), index);
        return elements.get(String.format(EXPRESSION_SYMBOL, index));
    }

    @Override
    public Element indexAt(String index) {
        checkNotNull(index);
        if (index.equals(PARAGRAPH_SYMBOL))
            return rootElement;
        return elements.get(index);
    }

    @Override
    public Iterator<Element> iterator() {
        return new ParagraphIterator();
    }

    private class ParagraphIterator implements Iterator<Element> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < elements.size();
        }

        @Override
        public Element next() {
            return elements.get(String.format(EXPRESSION_SYMBOL, index++));
        }
    }
}
