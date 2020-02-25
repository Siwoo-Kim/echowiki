package org.echowiki.core.expression;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class UnitTestSimpleExpressionEngine {

    SimpleExpressionEngine engine = new SimpleExpressionEngine();

    String WIKI_EXPRESSION =
            "== {p:{!:'개요'} ==\n" +
            "\n" +
            "{!:'시작'} 해볼까요? \n" +
            "{!color(skyblue):에코 표현식}을 사용해볼게요. {+(여기를 클릭):이렇게 주석도 가능합니다}\n" +
            "표현식은 \\{\\} 을 사용하여 정의합니다. {!bgcolor(red):{!:'조심'}} 하세요.\n" +
            " ( '\\{\\}' 은 {!:`에코 엔진`}에게 특별한 의미가 있답니다.)";
    
    String PARSED_EXPRESSION =
            "[@paragraph]\n" +
            "== [@echo0] ==\n" +
            "\n" +
            "[@echo1] 해볼까요? \n" +
            "[@echo2]\n" +
            "표현식은 \\{\\} 을 사용하여 정의합니다. [@echo3] 하세요.\n" +
            " ( '\\{\\}' 은 [@echo4]에게 특별한 의미가 있답니다.)";

    @Test
    public void unitTestEncodingParagraphExpectScopeExpression() {
        String TEXT = "=== {p:텍스트 배경 그라데이션 효과} ===\n" +
                "{!color(red):{!:'주의'}}: 정식 문법이 아니며 지원 중단 가능성이 있는 비권장 문법입니다.\n" +
                "{!bgcolor(blue):시작}\n" +
                "이 문법은 글자의 배경에 {+:주석}을 넣는 기능입니다. (텍스트 뿐만 아니라 테이블의 셀 배경으로 적용 또한 가능함) <헥스 코드 1, 2> 자리에 자신이 넣고 싶은 여섯 자리의 [[헥스 코드]]들을 찾아서 입력해 주세요.\n" +
                " {nli:왼쪽에서 오른쪽}\n" +
                " {nli:오른쪽에서 왼쪽}\n" +
                " {nli:위에서 아래}\n" +
                " {nli:아래에서 위}\n" +
                "그라데이션의 각도를 세세하게 조절하고 싶다면 <to 방향> 대신 <숫자deg> 문법을 사용해 보세요. 숫자 안의 각도 숫자를 자유자재로 조절할 수 있습니다. 아래는 예시입니다.\n" +
                " {li:북쪽 방향: 0deg}\n" +
                " {li:북동쪽 방향: 45deg}\n" +
                " {li:동쪽 방향: 90deg}\n" +
                "=== {/p} ===";
        Paragraph paragraph = engine.encodingParagraph(TEXT);
    }
}
