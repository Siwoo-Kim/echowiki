package org.echowiki.core.expression;


import org.apache.logging.log4j.util.Strings;

import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ScopeExpressionParser extends AbstractExpressionParser implements ExpressionParser {
    public static final char OPEN_BRACKET = '=';
    public static final char CLOSE_BRACKET = '=';
    private static final Pattern SCOPE_PATTERN = Pattern.compile("={2,} .* ={2,}");
    private final EchoExpressionParser echoParser = new EchoExpressionParser();

    @Override
    public ScopeExpression parse(String expString) {
        checkArgument(Strings.isNotBlank(expString));
        expString = expString.trim();
        String[] lines = expString.split("\n");
        if (lines[0].charAt(0) != OPEN_BRACKET)
            return ScopeExpressionFactory.newInstance(expString, null, null, null, null);
        else
            return internalParse(expString);
    }

//    String[] getScopeBoundaries(String rawText) {
//
//    }

    public boolean scopeExpressionEnd(String line) {
        return scopeClosed(line);
    }

    public boolean scopeExpressionStart(String line) {
        checkNotNull(line);
        line = line.trim();
        return SCOPE_PATTERN.matcher(line).matches();
    }

    private ScopeExpression internalParse(String expString) {
        String[] lines = expString.split("\n");
        validateScope(expString);
        String startLine = lines[0];
        String echoExpression = getEchoExpressionInString(startLine);
        String wrapper = getWrapperInString(startLine);
        String exp = echoParser.getExpressionInString(echoExpression);
        String arguments = echoParser.getArgumentsInString(echoExpression);
        String value = echoParser.getValueInString(echoExpression);
        return ScopeExpressionFactory.newInstance(expString, exp, arguments, value, wrapper);
    }

    void validateScope(String expString) {
        String[] splittedLine = expString.split("\n");
        String startLine = splittedLine[0];
        String lastLine = splittedLine[splittedLine.length - 1];
        if (startLine.charAt(0) != OPEN_BRACKET)
            throw new MalformedExpressionException(String.format("Malformed Scope Expression [%s]", startLine));
        validateWrapperConsistency(startLine);
        if (splittedLine.length != 1 && isWrapped(lastLine, OPEN_BRACKET, CLOSE_BRACKET)) {
            validateWrapperConsistency(lastLine);
            String expression = getEchoExpressionInString(lastLine);
            expression = echoParser.getExpressionInString(expression);
            if (!expression.startsWith("/"))
                throw new MalformedExpressionException(String.format("Malformed Wrapper (On closed Wrapper) Expression [%s]", lastLine));
        }
    }

    private boolean scopeClosed(String line) {
        if (!isWrapped(line, OPEN_BRACKET, CLOSE_BRACKET))
            return false;
        String expression = getEchoExpressionInString(line);
        String expressionInEcho = echoParser.getExpressionInString(expression);
        return expressionInEcho.startsWith("/") && !isCharEscaped(expressionInEcho, 0);
    }

    private void validateWrapperConsistency(String wrappedLine) {
        wrappedLine = wrappedLine.trim();
        if (!isWrapped(wrappedLine, OPEN_BRACKET, CLOSE_BRACKET))
            throw new MalformedExpressionException(String.format("Malformed Wrapper in Expression [%s]", wrappedLine));
        String wrapper = getWrapperInString(wrappedLine);
        String[] splittedWrapper = wrapper.split(" ");
        if (!splittedWrapper[0].equals(splittedWrapper[1]))
            throw new MalformedExpressionException(String.format("Malformed Wrapper in Expression [%s]", wrappedLine));
    }

    String getWrapperInString(String startLine) {
        startLine = startLine.trim();
        StringBuilder stringBuilder = new StringBuilder();
        int start = 0;
        while (start < startLine.length()
                && (startLine.charAt(start) == OPEN_BRACKET))
            stringBuilder.append(startLine.charAt(start++));
        stringBuilder.append(" ");
        int ignore = start;
        while (ignore < startLine.length() &&
                (startLine.charAt(ignore) != CLOSE_BRACKET
                        || isCharEscaped(startLine, ignore)))
            ignore++;
        while (ignore < startLine.length())
            stringBuilder.append(startLine.charAt(ignore++));
        return stringBuilder.toString().trim();
    }

    String getEchoExpressionInString(String startLine) {
        startLine = startLine.trim();
        int start = 0;
        while (start < startLine.length()
                && (startLine.charAt(start) == OPEN_BRACKET))
            start++;
        int end = start;
        while (end < startLine.length() &&
                (startLine.charAt(end) != CLOSE_BRACKET ||
                        isCharEscaped(startLine, end)))
            end++;
        String exp = startLine.substring(start, end);
        return exp.trim();
    }
}
