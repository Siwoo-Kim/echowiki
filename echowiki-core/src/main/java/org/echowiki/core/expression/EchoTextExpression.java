package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.expression.element.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class EchoTextExpression extends AbstractEchoExpression {

    private static final WIKI WIKI_TEXT = WIKI.WIKI_TEXT;
    private static List<ExpressionProvider> providers = new ArrayList<>();

    static {
        providers.add(ExpressionProvider.builder()
                        .checkIdentifier(exp -> exp.equals("!"))
                        .initializer(TextStyleDecorator::new).build());
        providers.add(ExpressionProvider.builder()
                        .checkIdentifier(exp -> exp.equals("!size"))
                        .initializer(TextSizeDecorator::new).build());
        providers.add(ExpressionProvider.builder()
                .checkIdentifier(exp -> exp.equals("!color") || exp.equals("!bgcolor"))
                .initializer(TextColorDecorator::new).build());
    }

    EchoTextExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    public static EchoTextExpression newInstance(String expString, String expression, String rawValue, String arguments) {
        checkArgument(Strings.isNotBlank(expression));
        for (ExpressionProvider p: providers) {
            if (p.checkIdentifier.test(expression))
                return (EchoTextExpression) p.initializer.provide(expString, expression, rawValue, arguments);
        }
        throw new MalformedExpressionException(String.format("Unknown Expresion [%s]", expString));
    }

    @Override
    protected void hookElement(Element el) {
        el.addValue(WIKI_TEXT.key(), getAttributeValue());
    }

    abstract String getAttributeValue();

    private static class TextSizeDecorator extends EchoTextExpression {
        private static final String[] IDENTIFIERS = new String[]{"!size"};

        TextSizeDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        String[] identifiers() {
            return IDENTIFIERS.clone();
        }

        @Override
        String getAttributeValue() {
            return "size=" + arguments();
        }

    }

    private static class TextStyleDecorator extends EchoTextExpression {
        private static final char[] EXPRESSIONS_WRAPPER = {'\'', '`', '-', '_'};
        private static final String[] IDENTIFIERS = {"!"};
        private static final String ATTRIBUTE_KEY = "text-style";
        private static final String[] ATTRIBUTE_VALUES = {"bold", "italic", "del", "underline"};

        TextStyleDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        String[] identifiers() {
            return IDENTIFIERS.clone();
        }

        @Override
        String getAttributeValue() {
            String value = value();
            char firstChar = value.charAt(0);
            for (int i = 0; i < EXPRESSIONS_WRAPPER.length; i++)
                if (firstChar == EXPRESSIONS_WRAPPER[i])
                    return "style=" + ATTRIBUTE_VALUES[i];
            throw new MalformedExpressionException(
                    String.format("Malformed Expression [%s] for Text styling.", expressionString()));
        }
    }

    private static class TextColorDecorator extends EchoTextExpression {
        private static final String[] IDENTIFIERS = {"!color", "!bgcolor"};

        TextColorDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        String getAttributeValue() {
            for (int i = 0; i < IDENTIFIERS.length; i++)
                if (expression().equals(IDENTIFIERS[i]))
                    return "color=" + arguments();
            throw new MalformedExpressionException(
                    String.format("Malformed Expression [%s] for Text coloring.", expressionString()));
        }

        @Override
        String[] identifiers() {
            return IDENTIFIERS.clone();
        }
    }
}
