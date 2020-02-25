package org.echowiki.core.expression;

import lombok.AllArgsConstructor;
import org.echowiki.core.expression.element.*;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
            protected Scope getElementType() {
                return Scope.LINE;
            }

            @Override
            String[] identifiers() {
                return new String[0];
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
            protected Scope getElementType() {
                return Scope.LINE;
            }

            @Override
            String[] identifiers() {
                return new String[0];
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
            protected Scope getElementType() {
                return Scope.LINE;
            }

            @Override
            String[] identifiers() {
                return new String[0];
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
            protected Scope getElementType() {
                return Scope.LINE;
            }

            @Override
            String[] identifiers() {
                return new String[0];
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
                el.addValue("outerMost", "outerMost");
            }

            @Override
            protected Scope getElementType() {
                return Scope.LINE;
            }

            @Override
            String[] identifiers() {
                return new String[0];
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression outer = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "+", "{!color(orange):{!:'This is test'}}", null) {
            @Override
            protected void hookElement(Element el) {
                el.addValue("outer", "outer");
            }

            @Override
            protected Scope getElementType() {
                return Scope.LINE;
            }

            @Override
            String[] identifiers() {
                return new String[0];
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };
        AbstractExpression inner = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "!color", "{!:'This is test'}", "orange") {
            @Override
            protected void hookElement(Element el) {
                el.addValue("inner", "inner");
            }

            @Override
            protected Scope getElementType() {
                return Scope.LINE;
            }

            @Override
            String[] identifiers() {
                return new String[0];
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firedElement.add((Expression) evt.getSource());
            }
        };

        AbstractExpression innerMost = new AbstractExpression("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "!", "'This is test", "orange") {
            @Override
            protected void hookElement(Element el) {
                el.addValue("innerMost", "innerMost");
            }

            @Override
            protected Scope getElementType() {
                return Scope.LINE;
            }

            @Override
            String[] identifiers() {
                return new String[0];
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
        List<String> keys = element.attributes().stream().map(Attribute::key).collect(Collectors.toList());
        List<String> values = element.attributes().stream().map(Attribute::values).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(keys);
        assertThat(keys, hasItems(WIKI.WIKI_LITERAL.key(), "innerMost", "inner", "outer", "outerMost"));
        assertThat(values, hasItems("'This is test'", "innerMost",  "inner", "outer", "outerMost"));
    }

}
