package org.echowiki.core.expression;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnitTestEchoExpressionFactory {

    @Test
    public void unitTestNewInstanceWhenTextExpressionIsProvide() {
        Expression expression = EchoExpressionFactory.newInstance("{!:'123'}", "!", "'123'", null);
        assertThat(expression, is(instanceOf(EchoTextExpression.class)));

        expression = EchoExpressionFactory.newInstance("{!size(50):123}", "!size", "'123'", "50");
        assertThat(expression, is(instanceOf(EchoTextExpression.class)));

        expression = EchoExpressionFactory.newInstance("{!color(orange):123}", "!color", "'123'", "orange");
        assertThat(expression, is(instanceOf(EchoTextExpression.class)));

        expression = EchoExpressionFactory.newInstance("{!bgcolor(orange):123}", "!bgcolor", "'123'", "orange");
        assertThat(expression, is(instanceOf(EchoTextExpression.class)));
    }

    @Test
    public void unitTestNewInstanceWhenDocumentExpressionIsProvide() {
        Expression expression = EchoExpressionFactory.newInstance("{@(Korean History):One of Eastern Asia Kingdom", "@", "One of Eastern Asia Kingdom", "Korean History");
        assertThat(expression, is(instanceOf(EchoLinkDocumentExpression.class)));
    }

    @Test
    public void unitTestNewInstanceWhenListExpressionIsProvide() {
        Expression expression = EchoExpressionFactory.newInstance("{li:first element on the list", "li", "first element on the list", null);
        assertThat(expression, is(instanceOf(EchoListExpression.class)));

        expression = EchoExpressionFactory.newInstance("{nli:first element on the list", "nli", "first element on the list", null);
        assertThat(expression, is(instanceOf(EchoListExpression.class)));
    }

    @Test
    public void unitTestNewInstanceWhenEchoNoteExpressionIsProvide() {
        Expression expression = EchoExpressionFactory.newInstance("{+:this is note}", "+", "this is note", null);
        assertThat(expression, is(instanceOf(EchoNoteExpression.class)));
    }

    @Test
    public void untTestNewInstanceWhenLiteralExpressionIsProvide() {
        Expression expression = EchoExpressionFactory.newInstance("literal", null, "literal", null);
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
    }
    
}
