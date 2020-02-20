package org.echowiki.core.expression.parser;

import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;

public class EchoExpressionParser implements ExpressionParser {

    private static final String OPENING = "{{";
    private static final String CLOSING = "}}";
    private static final char ARGUMENT_SEPARATOR = ':';

    static class EchoElement implements ParsedElement {
        private final String raw;
        private String exp;
        private String value;
        private EchoElement child;
        private String arguments;

        public EchoElement(String exp, String raw) {
            checkArgument(exp.startsWith(OPENING));
            checkArgument(exp.endsWith(CLOSING));
            this.raw = raw;
            build(exp.substring(2, exp.length()-2));
            buildArguments();
        }

        private void buildArguments() {
            if (this.exp.matches("^.+\\(.+\\)$")) {
                String openingFunction = this.exp.substring(0, this.exp.length()-1);
                int index = this.raw.indexOf(openingFunction);
                index += this.exp.length() -
                        2;
                int start = index+1;
                int depth = 0;
                while (index < this.raw.length()) {
                    if (this.raw.charAt(index) == '(')
                        depth++;
                    else if (this.raw.charAt(index) == ')')
                        depth--;
                    if (depth == 0)
                        break;
                    index++;
                }

                checkElementIndex(index, this.raw.length());
                this.arguments = this.raw.substring(start, index);
            }
        }

        private void build(String exp) {
            int expIndex = exp.indexOf(ARGUMENT_SEPARATOR);
            int rawIndex = raw.indexOf(ARGUMENT_SEPARATOR);
            if (expIndex == -1)
                this.exp = exp;
            else {
                this.exp = exp.substring(0, expIndex);
                this.value = raw.substring(rawIndex+1);
            }
        }

        public boolean hasValue() {
            return value != null;
        }

        @Override
        public String toString() {
            return "EchoElement{" +
                    "raw='" + raw + '\'' +
                    ", exp='" + exp + '\'' +
                    ", value='" + value + '\'' +
                    ", child=" + child +
                    '}';
        }

        @Override
        public String raw() {
            return raw;
        }

        @Override
        public String arguments() {
            return arguments;
        }

        @Override
        public String value() {
            return value;
        }

        @Override
        public String expression() {
            return exp;
        }

        @Override
        public List<ParsedElement> children() {
            return Collections.singletonList(child);
        }
    }

    public List<ParsedElement> parse(String raw) {
        checkArgument(Strings.isNotBlank(raw));
        raw = raw.trim();
        List<ParsedElement> elements = new ArrayList<>();
        int start = 0;
        final int END_RAW = raw.length();
        while (start < END_RAW) {
            if (!isStartExpression(start, raw))
                start++;
            else {
                int depth = 0;
                Stack<StringBuilder> exp = new Stack<>();
                Stack<Integer> startIndex = new Stack<>();
                EchoElement echoElement = null;
                int i = start;
                for (; i<raw.length(); i++) {
                    char c = raw.charAt(i);
                    if (isStartExpression(i, raw)) {
                        depth++;
                        exp.push(new StringBuilder());
                        startIndex.push(i);
                        i++;
                        continue;
                    } else if (!isClosingExpression(i, raw)) {
                        StringBuilder sb = exp.peek();
                        sb.append(c);
                    } else if (isClosingExpression(i, raw)) {
                        StringBuilder sb = exp.pop();
                        if (echoElement == null){
                            echoElement = new EchoElement("{{" + sb.toString() + "}}", raw.substring(startIndex.pop(), i + 2));
                        } else {
                            EchoElement newRoot = new EchoElement("{{" + sb.toString() + "}}", raw.substring(startIndex.pop(), i + 2));
                            newRoot.child = echoElement;
                            echoElement = newRoot;
                        }
                        depth--;
                        i++;
                    }
                    if (depth == 0)
                        break;
                }
                elements.add(echoElement);
                start = i;
            }
        }
        return elements;
    }

    private boolean isClosingExpression(int start, String raw) {
        final int end = raw.length();
        if (start+1 >= end)
            return false;
        String closing = raw.substring(start, start+2);
        return CLOSING.equals(closing);
    }

    private boolean isStartExpression(int start, String raw) {
        final int end = raw.length();
        if (start+1 >= end)
            return false;
        String opening = raw.substring(start, start+2);
        return OPENING.equals(opening);
    }

    public static void main(String[] args) {
        EchoExpressionParser echoExpressionParser = new EchoExpressionParser();
        System.out.println(echoExpressionParser.parse(TEST_STRING));
    }

    private static final String TEST_STRING = "" +
            "<p heading=\"test\">" +
            "=== 텍스트 색상 ===\n" +
            "[anchor(textcolor)]텍스트의 색상을 조절할 수 있습니다.\n" +
            "\n" +
            "|| {{#RRGGBB:텍스트}}: {{#ff0000:FF0000}} or {{#f00:F00}} ||\n" +
            "|| {{#name:텍스트}}: {{#red:RED}} ||\n" +
            " * #이 색깔 이름 앞에 반드시 들어가야 합니다.\n" +
            " * RGB 값, 또는 CSS에서 사용할 수 있는 웹 색상을 이용할 수 있습니다. 대소문자는 구별하지 않습니다.\n" +
            "  * 색상값은 [[헥스 코드]] 문서 또는 [[https://en.wikipedia.org/wiki/Web_colors|웹 색상]], [[https://en.wikipedia.org/wiki/Lists_of_colors|색상 목록]]을 참고하시기 바랍니다.\n" +
            "  * #transparent, #RRGGBBAA 식의 투명한 값이 있는 색상은 지원하지 않습니다.\n" +
            " * {{나무위키:테마|{{text-size(10):다크 테마}}}}에서 표시될 색상을 별도로 지정할 수 있습니다.\n" +
            " * {{text-style(bold):{{text-size(10):안녕하세요!!}}}}에서 표시될 색상을 별도로 지정할 수 있습니다.\n" +
            "  * {{text-size(10)}}:  테마와 관계없이 언제나 동일한 색상이 적용됩니다.\n" +
            "{{문단({{text-color(orange):{{text-size(10):제목}}}}):조선왕조}}  테마와 관계없이 언제나 동일한 색상이 적용됩니다.\n" +
            "  * {{#RRGGBB:텍스트}}:  테마와 관계없이 언제나 동일한 색상이 적용됩니다.\n" +
            "  * {{#RRGGBB:텍스트}}: 첫 번째 색상은 라이트 테마, 두 번째 색상은 다크 테마에서 적용됩니다.\n" +
            "\n" +
            "RGB에서 앞에 두자리는 R 그 뒤 두 자리는 G 그 뒤 두 자리는 B의 강도를 수치화 한 것입니다.\n" +
            "R=red\n" +
            "G=green\n" +
            "B=blue\n{{b}}" +
            "수치는 [[10진법]]이 아닌 [[16진법]]을 사용 하며 00이 0%, FF가 100% 입니다.\n" +
            "----\n" +
            "'''{{!:'{{#red:참고}}'}}''': 밑줄 서식과 혼용할 때 두 가지 방법을 쓸 수 있습니다.\n" +
            " * {{#green:__밑줄 포함 녹색__}}: {{#green}}\n" +
            " * {{__{{#green:밑줄 제외 녹색}}__}}: __{{#green:밑줄 제외 녹색}}__\n" +
            "----\n" +
            "'''{{#red:주의}}''': 색상이 조절된 텍스트 안에서 개행하고자 할 때는 {{[br]}} 매크로를 사용하여 주십시오.\n" +
            "\n" +
            "</p>";

}
