package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;

import java.util.Stack;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;

public class EchoExpressionParser implements ExpressionParser {

    public static final char ESCAPE = '\\';
    public static final char OPEN_BRACKET = '{';
    public static final char CLOSE_BRACKET = '}';
    public static final char FUNCTION_OPEN_BRACKET = '(';
    public static final char FUNCTION_CLOSE_BRACKET = ')';
    public static final char VALUE_SEPARATOR = ':';

    //{exp[(args, args..)]?:[value|exp]}
    private static final Pattern pattern = Pattern.compile("^\\{.+}$");

    @Override
    public Expression parse(String expString) {
        Stack<String> tokens = new Stack<>();
        Stack<Integer> openBracketIndex = new Stack<>();
        for (int start = 0; start < expString.length(); start++) {
            char c = expString.charAt(start);
            if (c == OPEN_BRACKET && !isCharEscaped(expString, start)) {
                openBracketIndex.push(start);
            } else if (c == CLOSE_BRACKET && !isCharEscaped(expString, start)) {
                int startIndex = openBracketIndex.pop();
                tokens.push(expString.substring(startIndex, start + 1));
            }
        }
        String token = tokens.pop();
        AbstractEchoExpression rootExpression = (AbstractEchoExpression) internalParse(token);
        AbstractEchoExpression currentExpression = rootExpression;
        while (!tokens.isEmpty()) {
            token = tokens.pop();
            Expression childExpression = internalParse(token);
            if (childExpression instanceof LiteralExpression)
                break;
            currentExpression.innerExpression = childExpression;
            currentExpression = (AbstractEchoExpression) childExpression;
        }
        Expression expression = rootExpression;
        while (expression != null) {
            if ((expression instanceof AbstractEchoExpression)
                && expression.innerExpression() == null
                    && expression.value() != null) {
                ((AbstractEchoExpression) expression).innerExpression = new LiteralExpression(expression.value());
                break;
            }
            expression = expression.innerExpression();
        }
        return rootExpression;
    }

    private Expression internalParse(String expString) {
        checkArgument(Strings.isNotBlank(expString));
        String copiedExpString = stripOffWrapper(expString, OPEN_BRACKET, CLOSE_BRACKET);
        String value = getValueInString(copiedExpString);
        String exp = getExpressionInString(copiedExpString);
        String arguments = getArgumentsInString(copiedExpString);
        return EchoExpressionFactory.newInstance(expString, exp, value, arguments);
    }

    static boolean isExpression(String expString) {
        if (expString == null) return false;
        return pattern.matcher(expString).matches();
    }

    //{exp(args, args...):value} => args, args...
    //{exp(args, args...):{exp(args): value}} => args, args...
    private String getArgumentsInString(String expString) {
        if (isWrapped(expString, OPEN_BRACKET, CLOSE_BRACKET))
            expString = stripOffWrapper(expString, OPEN_BRACKET, CLOSE_BRACKET);
        int depth = 0, start = 0, end = expString.length();
        while (start < end) {
            char c = expString.charAt(start);
            if (c == OPEN_BRACKET && !isCharEscaped(expString, start))
                depth++;
            else if (c == CLOSE_BRACKET && !isCharEscaped(expString, start))
                depth--;
            if (depth == 0 && c == FUNCTION_OPEN_BRACKET && !isCharEscaped(expString, start)) {
                int endIndexOfBracket = start;
                for (; (endIndexOfBracket < end &&
                        expString.charAt(endIndexOfBracket) != FUNCTION_CLOSE_BRACKET) ||
                        isCharEscaped(expString, endIndexOfBracket); endIndexOfBracket++)
                    ;
                return expString.substring(start + 1, endIndexOfBracket);
            }
            start++;
        }
        return null;
    }

    //exp(args, args...):[value|{exp}]
    private String getExpressionInString(String expString) {
        if (isWrapped(expString, OPEN_BRACKET, CLOSE_BRACKET))
            expString = stripOffWrapper(expString, OPEN_BRACKET, CLOSE_BRACKET);
        int start = 0, end = expString.length();
        while (start < end) {
            char c = expString.charAt(start);
            if (c == VALUE_SEPARATOR || c == FUNCTION_OPEN_BRACKET)
                return expString.substring(0, start);
            start++;
        }
        return expString;
    }

    private String getValueInString(String expression) {
        int index = getIndexOfValueSeparator(expression);
        if (index == -1)
            return null;
        else
            return expression.substring(index + 1);
    }

    //exp:value => index of :
    //exp:{exp:value} => index of first separator
    private int getIndexOfValueSeparator(String expString) {
        if (isWrapped(expString, OPEN_BRACKET, CLOSE_BRACKET))
            expString = stripOffWrapper(expString, OPEN_BRACKET, CLOSE_BRACKET);
        int depth = 0, start = 0, length = expString.length();
        while (start < length) {
            char c = expString.charAt(start);
            if (c == '{' && !isCharEscaped(expString, start))
                depth++;
            else if (c == '}' && !isCharEscaped(expString, start))
                depth--;
            if (depth == 0 && c == VALUE_SEPARATOR && !isCharEscaped(expString, start))
                return start;
            start++;
        }
        return -1;
    }

    private boolean isCharEscaped(String expString, int start) {
        checkElementIndex(start, expString.length());
        if (start == 0) return false;
        return expString.charAt(start - 1) == ESCAPE;
    }

    private boolean isWrapped(String expString, char openBracket, char closeBracket) {
        expString = expString.trim();
        return expString.charAt(0) == openBracket
                && expString.charAt(expString.length() - 1) == closeBracket;
    }

    private String stripOffWrapper(String expString, char openBracket, char closeBracket) {
        expString = expString.trim();
        checkArgument(expString.charAt(0) == openBracket);
        checkArgument(expString.charAt(expString.length() - 1) == closeBracket);
        return expString.substring(1, expString.length() - 1);
    }

}
