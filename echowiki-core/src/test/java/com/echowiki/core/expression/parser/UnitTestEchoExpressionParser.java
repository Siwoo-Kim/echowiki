package com.echowiki.core.expression.parser;

import org.echowiki.core.expression.parser.EchoExpressionParser;
import org.echowiki.core.expression.parser.ParsedElement;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UnitTestEchoExpressionParser {

    EchoExpressionParser echoExpressionParser = new EchoExpressionParser();

    @Test
    public void unitTestParseTextExpression() {
        String raw = "|| {{text-size(10):텍스트1}}: {{text-color(orange):텍스트2}} or  → ~~취소선 1~~ || ||";

        List<ParsedElement> elements = echoExpressionParser.parse(raw);
        assertEquals(elements.get(0).raw(), "{{text-size(10):텍스트1}}");
        assertEquals(elements.get(0).expression(), "text-size(10)");
        assertEquals(elements.get(0).value(), "텍스트1");

        assertEquals(elements.get(1).raw(), "{{text-color(orange):텍스트2}}");
        assertEquals(elements.get(1).expression(), "text-color(orange)");
        assertEquals(elements.get(1).value(), "텍스트2");

        raw = "|| {{!:'굵게'}} → '''굵게 ''' [* 큰따옴표(\") 한 개, 작은따옴표(')한 개가 아니라 {{!:`작은따옴표`}}(')를 세 번 입력한 상태입니다.] ||\n";
        elements = echoExpressionParser.parse(raw);
        assertEquals(elements.get(0).raw(), "{{!:'굵게'}}");
        assertEquals(elements.get(0).expression(), "!");
        assertEquals(elements.get(0).value(), "'굵게'");

        assertEquals(elements.get(1).raw(), "{{!:`작은따옴표`}}");
        assertEquals(elements.get(1).expression(), "!");
        assertEquals(elements.get(1).value(), "`작은따옴표`");

        raw = "|| __밑줄__ → {{!:_밑줄_}} [* _ (언더바)를 두 번씩 입력한 상태입니다.] this is italic {{text-i:italic string}} ||\n";
        elements = echoExpressionParser.parse(raw);
        assertEquals(elements.get(0).raw(), "{{!:_밑줄_}}");
        assertEquals(elements.get(0).expression(), "!");
        assertEquals(elements.get(0).value(), "_밑줄_");

        assertEquals(elements.get(1).raw(), "{{text-i:italic string}}");
        assertEquals(elements.get(1).expression(), "text-i");
        assertEquals(elements.get(1).value(), "italic string");
    }


    @Test
    public void unitTestParseNestedTextExpression() {
        String raw = "{{문단({{text-color(orange):{{text-size(10):제목}}}}):조선왕조}}  테마와 관계없이 언제나 동일한 색상이 적용됩니다.\n";
        List<ParsedElement> elements = echoExpressionParser.parse(raw);
        ParsedElement root = elements.get(0);
        assertEquals(root.raw(), "{{문단({{text-color(orange):{{text-size(10):제목}}}}):조선왕조}}");
        assertEquals(root.expression(), "문단()");
        assertEquals(root.arguments(), "{{text-color(orange):{{text-size(10):제목}}}}");
        assertEquals(root.value(), "{{text-size(10):제목}}}}):조선왕조}}");

        ParsedElement child = root.children().get(0);
        assertEquals(child.raw(), "{{text-color(orange):{{text-size(10):제목}}}}");
        assertEquals(child.expression(), "text-color(orange)");
        assertEquals(child.arguments(), "orange");
        assertEquals(child.value(), "{{text-size(10):제목}}}}");

        child = child.children().get(0);
        assertEquals(child.raw(), "{{text-size(10):제목}}");
        assertEquals(child.expression(), "text-size(10)");
        assertEquals(child.arguments(), "10");
        assertEquals(child.value(), "제목");
    }
}
