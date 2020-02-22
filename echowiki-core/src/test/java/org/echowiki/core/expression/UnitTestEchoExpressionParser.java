package org.echowiki.core.expression;

import org.echowiki.core.expression.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnitTestEchoExpressionParser {

    EchoExpressionParser echoExpressionParser = new EchoExpressionParser();

    @Test
    public void unitTestParseTextExpression() {
        String exp = "{+:이것은 각주입니다.}";
        EchoExpressionParser parser = new EchoExpressionParser();
        Expression expression = parser.parse(exp);
        assertEquals(expression.expression(), "+");
        assertEquals(expression.value(), "이것은 각주입니다.");
        assertEquals(expression.expressionString(), "{+:이것은 각주입니다.}");
        assertThat(expression, is(instanceOf(EchoNoteExpression.class)));
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
        assertEquals(expression.value(), "이것은 각주입니다.");

        exp = "{!:'굵은글씨'}";
        parser = new EchoExpressionParser();
        expression = parser.parse(exp);
        assertEquals(expression.expression(), "!");
        assertEquals(expression.value(), "'굵은글씨'");
        assertEquals(expression.expressionString(), "{!:'굵은글씨'}");
        assertThat(expression, is(instanceOf(EchoTextExpression.class)));
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
        assertEquals(expression.value(), "'굵은글씨'");

        exp = "{!:-{!:'굵은글씨'}-}";
        expression = parser.parse(exp);
        assertEquals(expression.expression(), "!");
        assertEquals(expression.value(), "-{!:'굵은글씨'}-");
        assertEquals(expression.expressionString(), "{!:-{!:'굵은글씨'}-}");
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertEquals(expression.expression(), "!");
        assertEquals(expression.value(), "'굵은글씨'");
        assertEquals(expression.expressionString(), "{!:'굵은글씨'}");
        assertThat(expression, is(instanceOf(EchoTextExpression.class)));
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
        assertEquals(expression.value(), "'굵은글씨'");

        exp = "{!:`{!size(10):색칠되고 italic's 글씨체}`}";
        expression = parser.parse(exp);
        assertEquals(expression.expression(), "!");
        assertEquals(expression.value(), "`{!size(10):색칠되고 italic's 글씨체}`");
        assertEquals(expression.expressionString(), "{!:`{!size(10):색칠되고 italic's 글씨체}`}");
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertEquals(expression.expression(), "!size");
        assertEquals(expression.value(), "색칠되고 italic's 글씨체");
        assertEquals(expression.expressionString(), "{!size(10):색칠되고 italic's 글씨체}");
        assertEquals(expression.arguments(), "10");
        expression = expression.innerExpression();
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
        assertEquals(expression.value(), "색칠되고 italic's 글씨체");

        exp = "{!size(10):{!:`색칠되고 italic's 글씨체`}}";
        expression = parser.parse(exp);
        assertEquals(expression.expression(), "!size");
        assertEquals(expression.expressionString(), "{!size(10):{!:`색칠되고 italic's 글씨체`}}");
        assertEquals(expression.arguments(), "10");
        expression = expression.innerExpression();
        assertEquals(expression.expression(), "!");
        assertEquals(expression.value(), "`색칠되고 italic's 글씨체`");
        assertEquals(expression.expressionString(), "{!:`색칠되고 italic's 글씨체`}");
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
        assertEquals(expression.value(), "`색칠되고 italic's 글씨체`");

        exp = "{@(조선):{!:'1800년대 우리나라의 왕국'}}";
        expression = parser.parse(exp);
        assertEquals(expression.expression(), "@");
        assertEquals(expression.value(), "{!:'1800년대 우리나라의 왕국'}");
        assertEquals(expression.expressionString(),
                "{@(조선):{!:'1800년대 우리나라의 왕국'}}");
        assertEquals(expression.arguments(), "조선");
        expression = expression.innerExpression();
        assertEquals(expression.expression(), "!");
        assertEquals(expression.value(), "'1800년대 우리나라의 왕국'");
        assertEquals(expression.expressionString(), "{!:'1800년대 우리나라의 왕국'}");
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
        assertEquals(expression.value(), "'1800년대 우리나라의 왕국'");

        exp = "{@(조선, 1-3):{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}}";
        expression = parser.parse(exp);
        expression.evaluate();
        assertEquals(expression.expression(), "@");
        assertEquals(expression.value(), "{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}");
        assertEquals(expression.expressionString(),
                "{@(조선, 1-3):{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}}");
        assertEquals(expression.arguments(), "조선, 1-3");
        expression = expression.innerExpression();
        assertEquals(expression.expression(), "!");
        assertEquals(expression.value(), "'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'");
        assertEquals(expression.expressionString(), "{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}");
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertEquals(expression.expression(), "!bgcolor");
        assertEquals(expression.value(), "1800년대 우리나라의 왕국 경제상황");
        assertEquals(expression.expressionString(), "{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}");
        assertEquals(expression.arguments(), "orange");
        expression = expression.innerExpression();
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
        assertEquals(expression.value(), "1800년대 우리나라의 왕국 경제상황");
    }

    @Test
    public void testEventListener() {
        String exp = "{@(조선, 1-3):{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}}";
        Expression expression = echoExpressionParser.parse(exp);
        assertEquals(expression.expression(), "@");
        assertEquals(expression.value(), "{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}");
        assertEquals(expression.expressionString(),
                "{@(조선, 1-3):{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}}");
        assertEquals(expression.arguments(), "조선, 1-3");
        expression = expression.innerExpression();
        assertEquals(expression.expression(), "!");
        assertEquals(expression.value(), "'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'");
        assertEquals(expression.expressionString(), "{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}");
        assertNull(expression.arguments());
        expression = expression.innerExpression();
        assertEquals(expression.expression(), "!bgcolor");
        assertEquals(expression.value(), "1800년대 우리나라의 왕국 경제상황");
        assertEquals(expression.expressionString(), "{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}");
        assertEquals(expression.arguments(), "orange");
        expression = expression.innerExpression();
        assertThat(expression, is(instanceOf(LiteralExpression.class)));
        assertEquals(expression.value(), "1800년대 우리나라의 왕국 경제상황");
    }
}
