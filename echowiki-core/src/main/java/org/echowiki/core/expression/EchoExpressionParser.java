package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

public class EchoExpressionParser extends AbstractExpressionParser implements ExpressionParser {

    public static final char OPEN_BRACKET = '{';
    public static final char CLOSE_BRACKET = '}';
    public static final char FUNCTION_OPEN_BRACKET = '(';
    public static final char FUNCTION_CLOSE_BRACKET = ')';
    public static final char VALUE_SEPARATOR = ':';

    //{exp[(args, args..)]?:[value|exp]}
    private static final Pattern ECHO_PATTERN = Pattern.compile("^\\{.+}$");
    //negative lookbehind
    private static final Pattern LINE_PATTERN = Pattern.compile("(?<!\\\\)\\{.*?([^\\\\]})+}?");

    static boolean isEchoExpression(String expString) {
        if (Strings.isBlank(expString)) return false;
        return ECHO_PATTERN.matcher(expString).matches();
    }

    @Override
    public Expression parse(String expString) {
        checkArgument(Strings.isNotBlank(expString));
        Stack<String> tokens = new Stack<>();
        Stack<Integer> openBracketIndex = new Stack<>();
        //find tokens which is wrapped in '{}'
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
        AbstractExpression rootExpression = (AbstractExpression) internalParse(token);
        AbstractExpression currentExpression = rootExpression;
        //adding expression from outer to nested
        while (!tokens.isEmpty()) {
            token = tokens.pop();
            Expression childExpression = internalParse(token);
            currentExpression.addExpression(childExpression);
            currentExpression = (AbstractExpression) childExpression;
        }
        //check the innermost expression has value
        //if it is creating literal expression
        Expression expression = rootExpression;
        while (expression != null) {
            if ((expression instanceof AbstractExpression)
                    && expression.innerExpression() == null
                    && expression.value() != null) {
                expression.addExpression(
                        EchoExpressionFactory.newInstance(expression.value(), null, null, null));
                break;
            }
            expression = expression.innerExpression();
        }
        return rootExpression;
    }

    @Override
    public boolean isExpression(String expression) {
        return isEchoExpression(expression);
    }

    @Override
    public boolean hasExpression(String string) {
        return hasEchoExpressionInLine(string);
    }

    private Expression internalParse(String expString) {
        checkArgument(Strings.isNotBlank(expString));
        String copiedExpString = stripOffWrapper(expString, OPEN_BRACKET, CLOSE_BRACKET);
        String value = getValueInString(copiedExpString);
        String exp = getClassNameInExpression(copiedExpString);
        String arguments = getArgumentsInString(copiedExpString);
        return EchoExpressionFactory.newInstance(expString, exp, value, arguments);
    }

    /**
     * Get class arguments in the expression
     *
     * @param expString
     * @return
     */
    //{exp(args, args...):value} => args, args...
    //{exp(args, args...):{exp(args): value}} => args, args...
    String getArgumentsInString(String expString) {
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

    /**
     * returns class name from the expression.
     *
     * eg -> exp(args, args...):[value|{exp}] => exp
     * eg -> exp:[value|{exp}] => exp
     * eg -> exp => exp
     *
     * @param expString
     * @return
     */
    String getClassNameInExpression(String expString) {
        expString = expString.trim();
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

    String getValueInString(String expression) {
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

    /**
     * does the raw string have echo expression?
     *
     * @param line
     * @return
     */
    public boolean hasEchoExpressionInLine(String line) {
        if (Strings.isBlank(line)) return false;
        if (line.contains("\n")) throw new IllegalArgumentException(
                String.format("Remove the \n in the line [%s]", line));
        return LINE_PATTERN.matcher(line).find() || LINE_PATTERN.matcher(line).matches();
    }

    //bug
    public String getFirstEchoExpressionInLine(String line) {
        Matcher matcher = LINE_PATTERN.matcher(line);
        if (!matcher.find())
            throw new IllegalArgumentException(String.format("Cannot find Echo Expression from %s", line));
        return line.substring(matcher.start(), matcher.end());
    }
}
