package org.echowiki.core.expression;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

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
    public void unitTestGetExpressionInString() {
        String string = "{@(조선, 1-3):{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}}";
        String expression = echoExpressionParser.getClassNameInExpression(string);
        assertEquals(expression, "@");

        string = "{!:'{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}'}";
        expression = echoExpressionParser.getClassNameInExpression(string);
        assertEquals(expression, "!");

        string = "{!bgcolor(orange):1800년대 우리나라의 왕국 경제상황}";
        expression = echoExpressionParser.getClassNameInExpression(string);
        assertEquals(expression, "!bgcolor");

        string = "1800년대 우리나라의 왕국 경제상황";
        expression = echoExpressionParser.getClassNameInExpression(string);
        System.out.println(expression);
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

    @Test
    public void unitTestHasExpressionInLine() {
        String line = "{!color(red):{!:'주의'}}: 정식 문법이 아니며 지원 중단 가능성이 있는 비권장 문법입니다.";
        boolean result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertTrue(result);
        line = "{!bgColor(blue):시작}";
        result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertTrue(result);
        line = "이 문법은 글자의 배경에 {+:주석}을 넣는 기능입니다. (텍스트 뿐만 아니라 테이블의 셀 배경으로 적용 또한 가능함) <헥스 코드 1, 2> 자리에 자신이 넣고 싶은 여섯 자리의 [[헥스 코드]]들을 찾아서 입력해 주세요.";
        result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertTrue(result);
        line = " {nli:왼쪽에서 오른쪽}";
        result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertTrue(result);
        line = "그러데이션의 여백을 조절하고 \\{+:이것은 탈출할 표현식\\} 하지만 이것은 유효한 표현식  {+:이것은 \\유효해\\}} 싶다면 위 문법에 margin: (세로 여백 조절 숫자)px (가로 여백 조절 숫자)px; 을 추가로 입력하여 조절해 주세요.";
        result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertTrue(result);
        line = "이 문법은 글자의 배경에 {+:주석}을 넣는 기능입니다. {@(Help Document):도움말}들을 찾아서 입력해 주세요.";
        result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertTrue(result);
        line = "그러데이션의 여백을 조절하고 \\{+:이것은 탈출할 표현식\\} 하지만 이것은 유효한 표현식 싶다면 위 문법에 margin: (세로 여백 조절 숫자)px (가로 여백 조절 숫자)px; 을 추가로 입력하여 조절해 주세요.";
        result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertFalse(result);
        line = "그라데이션의 각도를 세세하게 {} 조절하고 싶다면 <to 방향> 대신 <숫자deg> 문법을 사용해 보세요. 숫자 안의 각도 숫자를 자유자재로 조절할 수 있습니다. 아래는 예시입니다.";
        result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertFalse(result);
        line = "[anchor(tablegra)]표 문법 안에 넣어서 활용하는 방법도 있습니다.";
        result = echoExpressionParser.hasEchoExpressionInLine(line);
        assertFalse(result);
    }

    @Test
    public void uniTestGetEchoExpressionInLine() {
        String line = "{!color(red):{!:'주의'}}: 정식 문법이 아니며 지원 중단 가능성이 있는 비권장 문법입니다.";
        String result = echoExpressionParser.getFirstEchoExpressionInLine(line);
        assertEquals(result, "{!color(red):{!:'주의'}}");
        line = "{!bgColor(blue):시작}";
        result = echoExpressionParser.getFirstEchoExpressionInLine(line);
        assertEquals(result, "{!bgColor(blue):시작}");
        line = "이 문법은 글자의 배경에 {+:주석}을 넣는 기능입니다. (텍스트 뿐만 아니라 테이블의 셀 배경으로 적용 또한 가능함) <헥스 코드 1, 2> 자리에 자신이 넣고 싶은 여섯 자리의 [[헥스 코드]]들을 찾아서 입력해 주세요.";
        result = echoExpressionParser.getFirstEchoExpressionInLine(line);
        assertEquals(result, "{+:주석}");
        line = " {nli:왼쪽에서 오른쪽}";
        result = echoExpressionParser.getFirstEchoExpressionInLine(line);
        assertEquals(result, "{nli:왼쪽에서 오른쪽}");
        line = "그러데이션의 여백을 조절하고 \\{+:이것은 탈출할 표현식\\} 하지만 이것은 유효한 표현식  {+:이것은 \\유효해\\}} 싶다면 위 문법에 margin: (세로 여백 조절 숫자)px (가로 여백 조절 숫자)px; 을 추가로 입력하여 조절해 주세요.";
        result = echoExpressionParser.getFirstEchoExpressionInLine(line);
        assertEquals(result, "{+:이것은 \\유효해\\}}");
        line = "이 문법은 글자의 배경에 {+:{!:'주석'}}을 넣는 기능입니다. {@(Help Document):도움말}들을 찾아서 입력해 주세요.";
        result = echoExpressionParser.getFirstEchoExpressionInLine(line);
        assertEquals(result, "{+:{!:'주석'}}");
        line = "을 찾아서 입력해 주세요. {!:'{!bgcolor(red):주의}'}";
        result = echoExpressionParser.getFirstEchoExpressionInLine(line);
        assertEquals(result, "{!:'{!bgcolor(red):주의}'}");
    }

}
