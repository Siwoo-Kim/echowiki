package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Attribute;
import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.Scope;
import org.echowiki.core.expression.element.WIKI;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.util.*;
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
        Expression outerMost = EchoExpressionFactory
                .newInstance("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "@", "{+:{!color(orange):{!:'This is test'}}}", "한국사");
        Expression outer = EchoExpressionFactory
                .newInstance("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "+", "{!color(orange):{!:'This is test'}}", null);

        Expression inner = EchoExpressionFactory
                .newInstance("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "!color", "{!:'This is test'}", "orange");


        Expression innerMost = EchoExpressionFactory
                .newInstance("{@(한국사):{+:{!color(orange):{!:'This is test'}}}", "!", "'This is test", "orange");


        AbstractExpression literal = new LiteralExpression("'This is test'", null, null, null);
        outerMost.addExpression(outer);
        outer.addExpression(inner);
        inner.addExpression(innerMost);
        innerMost.addExpression(literal);
        Element element = outerMost.evaluate();
        List<String> keys = element.attributes().stream().map(Attribute::key).collect(Collectors.toList());
        List<String> values = element.attributes().stream().map(Attribute::values).flatMap(Collection::stream).collect(Collectors.toList());
        assertThat(keys, hasItems(WIKI.WIKI_LITERAL.key(), "wiki-text", "wiki-text", "wiki-note", "wiki-link"));
        assertThat(new HashSet<>(values), hasItems("literal='This is test'", "style=bold", "color=orange", "message=null", "doc=한국사"));
    }

}
