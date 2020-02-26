package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

public class SimpleWikiExpressionEngine implements WikiExpressionEngine {

    private static final EchoExpressionParser echoParser = new EchoExpressionParser();
    private static final ScopeExpressionParser scopeParser = new ScopeExpressionParser();

    public List<Paragraph> encodingDocument(String rawText) {
        checkArgument(rawText != null);
        if (Strings.isBlank(rawText))
            return Collections.emptyList();
        List<Paragraph> paragraphContexts = new ArrayList<>();
        String[] lines = rawText.split("[\n]");
        for (int start=0; start<lines.length; ) {
            String line = lines[start];
            List<String> linesInContext = new ArrayList<>();
            if (scopeParser.scopeExpressionStart(line)) {
                do {
                    linesInContext.add(line);
                    ++start;
                    if (start >= lines.length)
                        break;
                    line = lines[start];
                } while ((!scopeParser.scopeExpressionStart(line)
                        && !scopeParser.scopeExpressionEnd(line)));
                if (scopeParser.scopeExpressionEnd(line)) {
                    linesInContext.add(line);
                    start++;
                }
                paragraphContexts.add(encodingParagraph(String.join("\n", linesInContext)));
            } else {
                //stub (the condition need?)
                while (!scopeParser.scopeExpressionStart(line))  {
                    linesInContext.add(line);
                    ++start;
                    if (start >= lines.length)
                        break;
                    line = lines[start];
                }
                String paragraphLine = String.join("\n", linesInContext);
                if (Strings.isNotBlank(paragraphLine))
                    paragraphContexts.add(encodingParagraph(paragraphLine));
            }
        }
        return paragraphContexts;
    }

    Paragraph encodingParagraph(String rawText) {
        Map<String, Expression> mapper = new LinkedHashMap<>();
        String[] lines = rawText.split("\n");
        String[] encodedLines = new String[lines.length];
        ScopeExpression scopeExpression = scopeParser.parse(rawText);
        if (scopeExpression instanceof ParagraphScopeExpression) {
            String scopeExp = scopeParser.getWrappedStringInScopeExpression(lines[0]);
            encodedLines[0] = Pattern.compile(scopeExp, Pattern.LITERAL)
                    .matcher(lines[0])
                    .replaceFirst(Matcher.quoteReplacement("[@paragraph]"));
        }
        int start = scopeExpression instanceof ParagraphScopeExpression? 1: 0;
        int end = lines.length;
        int expressionId = 0;
        if (scopeExpression.closed()) {
            end--;
            String scopeExp = scopeParser.getWrappedStringInScopeExpression(lines[end]);
            encodedLines[end] = Pattern.compile(scopeExp, Pattern.LITERAL)
                    .matcher(lines[end])
                    .replaceFirst(Matcher.quoteReplacement("[/@paragraph]"));
        }
        for (; start < end; start++) {
            String line = lines[start];
            encodedLines[start] = line;
            while (echoParser.hasEchoExpressionInLine(line)) {
                String expression = echoParser.getFirstEchoExpressionInLine(line);
                Expression innerExpression = echoParser.parse(expression);
                scopeExpression.addExpression(innerExpression);
                String encodedId = String.format("[@echo%d]", expressionId++);
                mapper.put(encodedId, innerExpression);
                line = Pattern.compile(expression, Pattern.LITERAL).matcher(line)
                        .replaceFirst(Matcher.quoteReplacement(encodedId));
                encodedLines[start] = line;
            }
        }
        return new SimpleParagraph(lines, encodedLines, scopeExpression, mapper);
    }

    @Override
    public List<Paragraph> encoding(String rawText) {
        return encodingDocument(rawText);
    }
}
