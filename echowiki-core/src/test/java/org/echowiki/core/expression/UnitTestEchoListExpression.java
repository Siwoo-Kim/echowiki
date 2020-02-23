package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Attribute;
import org.echowiki.core.expression.element.AttributeKey;
import org.echowiki.core.expression.element.AttributeType;
import org.echowiki.core.expression.element.Element;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class UnitTestEchoListExpression {

    @Test
    public void unitTestGetIndexWhenScopeHasMultipleListItems() {
        String rawString = "== 문서1 ==\n" +
                "리스트를 시작해볼까?\n" +
                " \n" +
                "{#:리스트 아이템}\n" +
                "이건 그냥 일반 \n" +
                "{#:리스트 아이템}\n" +
                "{#:리스트 아이템/}\n" +
                "\n" +
                "{#:새로운 리스트 아이템}";
        ParagraphScopeExpression paragraph = new ParagraphScopeExpression(
                rawString, "문서1", null, null, "== ==");
        paragraph.addExpression(EchoExpressionFactory
                .newInstance("{#:리스트 아이템}", "#", "리스트 아이템", null));
        paragraph.addExpression(EchoExpressionFactory
                .newInstance("{#:리스트 아이템}", "#", "리스트 아이템", null));
        paragraph.addExpression(EchoExpressionFactory
                .newInstance("{#:리스트 아이템/}", "#", "리스트 아이템/", null));
        paragraph.addExpression(EchoExpressionFactory
                .newInstance("{#:새로운 리스트 아이템}", "#", "리스트 아이템", null));

        Element element = paragraph.getExpressionInScope().get(0).evaluate();
        List<Attribute> attributes = element.getAttributes(AttributeType.LIST, AttributeKey.WIKI_LIST_NUMBER.key());
        assertTrue(attributes.stream().anyMatch(e -> e.value().equals("item-1")));

        element = paragraph.getExpressionInScope().get(1).evaluate();
        attributes = element.getAttributes(AttributeType.LIST, AttributeKey.WIKI_LIST_NUMBER.key());
        assertTrue(attributes.stream().anyMatch(e -> e.value().equals("item-2")));

        element = paragraph.getExpressionInScope().get(2).evaluate();
        attributes = element.getAttributes(AttributeType.LIST, AttributeKey.WIKI_LIST_NUMBER.key());
        assertTrue(attributes.stream().anyMatch(e -> e.value().equals("item-3")));

        element = paragraph.getExpressionInScope().get(3).evaluate();
        attributes = element.getAttributes(AttributeType.LIST, AttributeKey.WIKI_LIST_NUMBER.key());
        assertTrue(attributes.stream().anyMatch(e -> e.value().equals("item-1")));
    }
}
