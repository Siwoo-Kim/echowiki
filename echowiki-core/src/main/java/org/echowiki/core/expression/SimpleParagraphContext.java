package org.echowiki.core.expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleParagraphContext implements ParagraphContext {

    private final String[] lines;
    private final String[] encodedLines;
    private final ScopeExpression scope;
    private Map<String, Expression> table;
    private Map<Expression, Element> elements;

    public SimpleParagraphContext(String[] lines, String[] encodedLines, ScopeExpression scopeExpression, Map<String, Expression> mapper) {
        this.lines = lines;
        this.encodedLines = encodedLines;
        this.scope = scopeExpression;
        this.table = mapper;
        elements = new HashMap<>();
        for (Expression exp: mapper.values()) {
            Element element = exp.evaluate();
            elements.put(exp, element);
        }
    }

    @Override
    public ScopeExpression getScopeExpression() {
        return null;
    }

    @Override
    public List<Element> getEchoExpressions() {
        return null;
    }

    @Override
    public String encodedString() {
        return null;
    }

    @Override
    public Element indexAt(int index) {
        return null;
    }
}
