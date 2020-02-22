package org.echowiki.core.expression;

import org.junit.Test;

public class UnitTestExpressionEngine {

    ExpressionEngine engine = new ExpressionEngine();

    @Test
    public void unitTestExpressionEngineEncoding() {
        String TEXT =    "=== {p:텍스트 배경 그라데이션 효과} ===\n" +
                "{!color(red):{!:'주의'}}: 정식 문법이 아니며 지원 중단 가능성이 있는 비권장 문법입니다.\n" +
                "\n" +
                "{!bgcolor(blue):시작}\n" +
                "이 문법은 글자의 배경에 {+:주석}을 넣는 기능입니다. (텍스트 뿐만 아니라 테이블의 셀 배경으로 적용 또한 가능함) <헥스 코드 1, 2> 자리에 자신이 넣고 싶은 여섯 자리의 [[헥스 코드]]들을 찾아서 입력해 주세요.\n" +
                "\n" +
                "그러데이션의 여백을 조절하고 \\{+:이것은 탈출할 표현식\\} 하지만 이것은 유효한 표현식 {+:이것은 \\유효해\\}} 싶다면 위 문법에 margin: (세로 여백 조절 숫자)px (가로 여백 조절 숫자)px; 을 추가로 입력하여 조절해 주세요.\n" +
                "\n" +
                "그라데이션의 크기를 조절하고 싶다면 위 문법에 padding: (세로 크기 조절 숫자)px (가로 크기 조절 숫자)px; 을 추가로 입력하여 조절해 주세요.\n" +
                "\n" +
                "그러데이션의 방향을 설정하고 싶다면 <to 방향> 자리에 to left, to right, to top, to bottom 중에서 하나를 입력해 주세요.\n" +
                "\n" +
                " {nli:왼쪽에서 오른쪽}\n" +
                " {nli:오른쪽에서 왼쪽}\n" +
                " {nli:위에서 아래}\n" +
                " {nli:아래에서 위}\n" +
                "\n" +
                "그라데이션의 각도를 세세하게 조절하고 싶다면 <to 방향> 대신 <숫자deg> 문법을 사용해 보세요. 숫자 안의 각도 숫자를 자유자재로 조절할 수 있습니다. 아래는 예시입니다.\n" +
                " {li:북쪽 방향: 0deg}\n" +
                " {li:북동쪽 방향: 45deg}\n" +
                " {li:동쪽 방향: 90deg}\n" +
                "=== {/p} ===";
        ParagraphContext context = engine.encoding(TEXT);

    }
}
