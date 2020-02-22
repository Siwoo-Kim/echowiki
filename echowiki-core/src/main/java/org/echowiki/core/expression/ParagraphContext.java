package org.echowiki.core.expression;

import java.util.List;

public interface ParagraphContext {

    ScopeExpression getScopeExpression();

    List<Element> getEchoExpressions();

    String encodedString();

    Element indexAt(int index);

}
