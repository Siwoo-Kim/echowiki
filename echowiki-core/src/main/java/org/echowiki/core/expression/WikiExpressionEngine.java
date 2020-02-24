package org.echowiki.core.expression;

import java.util.List;

public interface WikiExpressionEngine {

    /**
     * returns the {@link Paragraph} which is parsed from the given text.
     *
     * @param rawText
     * @return
     */
    List<Paragraph> encoding(String rawText);

}
