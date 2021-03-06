package org.echowiki.core.expression;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class UnitTestScopeExpressionParser {

    ScopeExpressionParser scopeExpressionParser = new ScopeExpressionParser();

    @Test
    public void unitTestParse() {
        String STRING = "== 개요 ==\n" +
                        "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                        "\n" +
                        "== {/} ==\n";
        ScopeExpression expression = scopeExpressionParser.parse(STRING);
        assertThat(expression, is(instanceOf(ParagraphScopeExpression.class)));
        assertEquals(expression.depth(), 0);

        STRING = "== {p:조선의 경제상황} ==\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n" +
                "== {/p} ==\n";
        expression = scopeExpressionParser.parse(STRING);
        assertThat(expression, is(instanceOf(ParagraphScopeExpression.class)));
        assertEquals(expression.depth(), 0);

        STRING = "=== {p} ===\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n";
        expression = scopeExpressionParser.parse(STRING);
        assertThat(expression, is(instanceOf(ParagraphScopeExpression.class)));
        assertEquals(expression.depth(), 1);
    }

    @Test
    public void unitTestScopeExpressionStart() {
        String STRING = "== 문서 ==";
        boolean result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertTrue(result);

        STRING = "=== 문서 ===";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertTrue(result);

        STRING = "== {p:문서} ==";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertTrue(result);

        STRING = "=== {p(arg):문서} ==";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertTrue(result);

        //correct but bad syntax
        STRING = "== 문서 ===";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertTrue(result);

        STRING = " ===  {p:=?zz}  ===";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertTrue(result);

        STRING = "이 글자는 신택스가 아닙니다";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertFalse(result);

        STRING = "이 글자도 == 신택스 == 가 아니죠";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertFalse(result);

        STRING = "== {/p} ==";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertFalse(result);

        STRING = "=== {/} ===";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertFalse(result);

        STRING = "== {/test} ==";
        result = scopeExpressionParser.scopeExpressionStart(STRING);
        assertFalse(result);;
    }

    @Test
    public void unitTestScopeExpressionEnd() {
        String STRING = "== {/p} ==";
        boolean result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertTrue(result);

        STRING = "=== {/} ===";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertTrue(result);

        STRING = "== {/test} ==";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertTrue(result);;

        STRING = "== 문서 ==";
         result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertFalse(result);

        STRING = "=== 문서 ===";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertFalse(result);

        STRING = "== {p:문서} ==";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertFalse(result);

        STRING = "=== {p(arg):문서} ==";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertFalse(result);

        //correct but bad syntax
        STRING = "== 문서 ===";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertFalse(result);

        STRING = " ===  {p:=?zz}  ===";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertFalse(result);

        STRING = "이 글자는 신택스가 아닙니다";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertFalse(result);

        STRING = "이 글자도 == 신택스 == 가 아니죠";
        result = scopeExpressionParser.scopeExpressionEnd(STRING);
        assertFalse(result);
    }

    @Test
    public void unitTestGetWrapperInString() {
        String STRING = "== 개요 ==";
        String wrapper = scopeExpressionParser.getWrapperInString(STRING);
        assertEquals(wrapper, "== ==");

        STRING = "=== {개요} ===\n";
        wrapper = scopeExpressionParser.getWrapperInString(STRING);
        assertEquals(wrapper, "=== ===");

        STRING = "==== {개요(arg):value} ====";
        wrapper = scopeExpressionParser.getWrapperInString(STRING);
        assertEquals(wrapper, "==== ====");

        STRING = "==== {개요(arg):\\=value} ====\n";
        wrapper = scopeExpressionParser.getWrapperInString(STRING);
        assertEquals(wrapper, "==== ====");
    }

    @Test
    public void unitTestGetExpressionInString() {
        String STRING =
                "== 개요 ==\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n" +
                "== /개요 ==\n";
        String expression = scopeExpressionParser.getWrappedStringInScopeExpression(STRING);
        assertEquals(expression, "개요");

        STRING = "== {개요} ==\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n" +
                "== /{개요} ==\n";
        expression = scopeExpressionParser.getWrappedStringInScopeExpression(STRING);
        assertEquals(expression, "{개요}");

        STRING = "== {개요(arg):value} ==\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n" +
                "== /개요 ==\n";
        expression = scopeExpressionParser.getWrappedStringInScopeExpression(STRING);
        assertEquals(expression, "{개요(arg):value}");

        STRING = "== {개요:\\=} ==\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n" +
                "== /{개요} ==\n";
        expression = scopeExpressionParser.getWrappedStringInScopeExpression(STRING);
        assertEquals(expression, "{개요:\\=}");
    }

    @Test(expected = MalformedExpressionException.class)
    public void unitTestValidateScopeExpression1() {
        String STRING = "=== 개요 ==\n" +
                        "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                        "\n" +
                        "== /개요 ==\n";
        scopeExpressionParser.validateScope(STRING);
    }

    @Test(expected = MalformedExpressionException.class)
    public void unitTestValidateScopeExpression2() {
        String STRING = "== 개요 ===\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n" +
                "== /개요 ==\n";
        scopeExpressionParser.validateScope(STRING);
    }

    @Test(expected = MalformedExpressionException.class)
    public void unitTestValidateScopeExpression3() {
        String STRING = "== 개요 ==\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n" +
                "=== /개요 ==\n";
        scopeExpressionParser.validateScope(STRING);
    }

    @Test(expected = MalformedExpressionException.class)
    public void unitTestValidateScopeExpression4() {
        String STRING = "== 개요 ==\n" +
                "이 문서는 [[나무위키]]의 위키 문법인 '''나무마크'''에 대하여 설명하는 문서입니다. 나무위키의 나무마크는 [[모니위키]]의 마크업과 100% 호환되지는 않습니다. 일부 기능은 모니위키에서 작동하던 것과 다르게 작동하는 경우가 있습니다. 모니위키에서 사용하던 일부 마크업은 계속 지원하고 있지만,  앞으로는 나무위키의 문법에 맞도록 수정 및 작성해주시기 바랍니다. 모니위키 호환 마크업은 예고 없이 지원이 중단될 수 있습니다.\n" +
                "\n" +
                "== 개요 ==\n";
        scopeExpressionParser.validateScope(STRING);
    }

    @Test
    public void unitTestValidateScopeExpression5() {
        String STRING = "== 개요 ==";
        scopeExpressionParser.validateScope(STRING);
    }
}
