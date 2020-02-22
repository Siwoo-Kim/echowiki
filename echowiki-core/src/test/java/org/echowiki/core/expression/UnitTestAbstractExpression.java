package org.echowiki.core.expression;

import lombok.AllArgsConstructor;
import org.echowiki.core.expression.meta.AttributeType;
import org.echowiki.core.expression.meta.ElementType;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class UnitTestAbstractExpression {

    @Test
    public void unitTestAddExpressionWhenTriggerEventsCheckOrder() {
        List<Expression> firedElement = new ArrayList<>();
        AbstractExpression outerMost = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "@", "{+:{!color(orange):{!:'This is test'}}}", "한국사") {
            @Override
            protected void hookElement(Element el) {
            }

            @Override
            protected ElementType getElementType() {
                return ElementType.ECHO;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression outer = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "+", "{!color(orange):{!:'This is test'}}", null) {
            @Override
            protected void hookElement(Element el) {
            }

            @Override
            protected ElementType getElementType() {
                return ElementType.ECHO;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression inner = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "!color", "{!:'This is test'}", "orange") {
            @Override
            protected void hookElement(Element el) {
            }

            @Override
            protected ElementType getElementType() {
                return ElementType.ECHO;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression innerMost = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "!", "'This is test", "orange") {
            @Override
            protected void hookElement(Element el) {
            }

            @Override
            protected ElementType getElementType() {
                return ElementType.ECHO;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };

        AbstractExpression literal = new LiteralExpression("'This is test'", null, null, null);
        outerMost.addExpression(outer);
        outer.addExpression(inner);
        inner.addExpression(innerMost);
        innerMost.addExpression(literal);
        outer.evaluate(); //fire events
        assertEquals(firedElement, Arrays.asList(literal, innerMost, inner, outer));
    }

    @Test
    public void unitTestEvaluateWhenSubclassOverridesHook() {
        List<Expression> firedElement = new ArrayList<>();
        AbstractExpression outerMost = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "@", "{+:{!color(orange):{!:'This is test'}}}", "한국사") {
            @Override
            protected void hookElement(Element el) {
                el.addAttribute(new TestAttribute("outerMost", "outerMost"));
            }

            @Override
            protected ElementType getElementType() {
                return ElementType.ECHO;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression outer = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "+", "{!color(orange):{!:'This is test'}}", null) {
            @Override
            protected void hookElement(Element el) {
                el.addAttribute(new TestAttribute("outer", "outer"));
            }

            @Override
            protected ElementType getElementType() {
                return ElementType.ECHO;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression inner = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "!color", "{!:'This is test'}", "orange") {
            @Override
            protected void hookElement(Element el) {
                el.addAttribute(new TestAttribute("inner", "inner"));
            }

            @Override
            protected ElementType getElementType() {
                return ElementType.ECHO;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression innerMost = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "!", "'This is test", "orange") {

            @Override
            protected void hookElement(Element el) {
                el.addAttribute(new TestAttribute("innerMost", "innerMost"));
            }

            @Override
            protected ElementType getElementType() {
                return ElementType.ECHO;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression literal = new LiteralExpression("'This is test'", null, null, null);
        outerMost.addExpression(outer);
        outer.addExpression(inner);
        inner.addExpression(innerMost);
        innerMost.addExpression(literal);
        Element element = outerMost.evaluate();
        List<String> keys = element.getAttributes().stream().map(Element.Attribute::key).collect(Collectors.toList());
        List<String> values = element.getAttributes().stream().map(Element.Attribute::value).collect(Collectors.toList());
        assertThat(keys, hasItems("outerMost", "outer", "inner", "innerMost", "literal"));
        assertThat(values, hasItems("outerMost", "outer", "inner", "innerMost", "'This is test'"));
    }

    @AllArgsConstructor
    private static class TestAttribute implements Element.Attribute {
        private final String key;
        private final String value;

        @Override
        public AttributeType type() {
            return null;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public String value() {
            return value;
        }
    }
}
