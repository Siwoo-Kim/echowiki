package org.echowiki.core.expression;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEngine {

    private static final EchoExpressionParser echoParser = new EchoExpressionParser();
    private static final ScopeExpressionParser scopeParser = new ScopeExpressionParser();

    public ParagraphContext encoding(String rawText) {
        Map<String, Expression> mapper = new HashMap<>();
        String[] lines = rawText.split("\n");
        String[] encodedLines = new String[lines.length];
        ScopeExpression scopeExpression = scopeParser.parse(rawText);
        if (scopeExpression instanceof ParagraphScopeExpression) {
            String scopeExp = scopeParser.getEchoExpressionInString(lines[0]);
            encodedLines[0] = Pattern.compile(scopeExp, Pattern.LITERAL)
                    .matcher(lines[0])
                    .replaceFirst(Matcher.quoteReplacement("[@paragraph]"));
        }
        int start = 1, end = lines.length;
        int expressionId = 0;
        if (scopeExpression.closed()) {
            end--;
            String scopeExp = scopeParser.getEchoExpressionInString(lines[end]);
            encodedLines[end] = Pattern.compile(scopeExp, Pattern.LITERAL)
                    .matcher(lines[end])
                    .replaceFirst(Matcher.quoteReplacement("[/@paragraph]"));
        }
        for (; start < end; start++) {
            String line = lines[start];
            encodedLines[start] = line;
            while (echoParser.hasEchoExpressionInLine(line)) {
                String expression = echoParser.getEchoExpressionInLine(line);
                Expression innerExpression = echoParser.parse(expression);
                scopeExpression.addExpression(innerExpression);
                String encodedId = String.format("[@echo%d]", expressionId++);
                mapper.put(encodedId, innerExpression);
                line = Pattern.compile(expression, Pattern.LITERAL).matcher(line)
                        .replaceFirst(Matcher.quoteReplacement(encodedId));
                encodedLines[start] = line;
            }
        }
        return new SimpleParagraphContext(lines, encodedLines, scopeExpression, mapper);
    }


}
