package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;

import java.util.*;

import static com.google.common.base.Preconditions.*;

public class SimpleParagraphContext implements ParagraphContext {

    private static final String EXPRESSION_SYMBOL = "[@echo%d]";
    private static final String PARAGRAPH_SYMBOL = "[@paragraph]";

    private final String[] decodedLines;
    private final String[] encodedLines;
    private final ScopeExpression context;
    private final Element scopeContext;
    private final Map<String, Expression> expressions;
    private final Map<String, Element> elements;

    public SimpleParagraphContext(String[] decodedLines,
                                  String[] encodedLines,
                                  ScopeExpression scopeExpression,
                                  Map<String, Expression> mapper) {
        checkArgument(decodedLines.length == encodedLines.length);
        checkArgument(scopeExpression != null);
        this.decodedLines = decodedLines.clone();
        this.encodedLines = encodedLines.clone();
        this.context = scopeExpression;
        this.scopeContext = scopeExpression.evaluate();
        this.expressions = new LinkedHashMap<>(mapper);
        elements = new LinkedHashMap<>();
        for (String key : expressions.keySet()) {
            Expression expression = expressions.get(key);
            elements.put(key, expression.evaluate());
        }
    }

    @Override
    public ScopeExpression getContext() {
        return context;
    }

    @Override
    public List<Element> getElements() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public String encodedString() {
        return String.join("", encodedLines);
    }

    @Override
    public String decodedString() {
        return String.join("", decodedLines);
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
            return scopeContext;
        return elements.get(index);
    }

    @Override
    public Iterator<Element> iterator() {
        return new ParagraphContextIterator();
    }

    private class ParagraphContextIterator implements Iterator<Element> {
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
