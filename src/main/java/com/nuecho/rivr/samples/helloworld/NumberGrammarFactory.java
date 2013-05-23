package com.nuecho.rivr.samples.helloworld;

import org.w3c.dom.*;

import com.nuecho.rivr.core.util.*;
import com.nuecho.rivr.voicexml.turn.output.grammar.*;

public final class NumberGrammarFactory {

    private static final String[] NUMBERS = {"zero",
                                             "one",
                                             "two",
                                             "three",
                                             "four",
                                             "five",
                                             "six",
                                             "seven",
                                             "eight",
                                             "nine"};

    public static InlineXmlGrammar generateNumberGrammar() {
        Document document = DomUtils.createDocument("grammar");

        Element grammarElement = document.getDocumentElement();
        grammarElement.setAttribute("type", "application/srgs+xml");
        grammarElement.setAttribute("root", "number");
        grammarElement.setAttribute("version", "1.0");

        Element ruleElement = DomUtils.appendNewElement(grammarElement, "rule");
        ruleElement.setAttribute("scope", "public");
        ruleElement.setAttribute("id", "number");

        Element oneOfElement = DomUtils.appendNewElement(ruleElement, "one-of");
        for (int i = 0; i < 10; i++) {
            createNumberAlternative(oneOfElement, i);
        }

        return new InlineXmlGrammar(document);
    }

    private static void createNumberAlternative(Element oneOfElement, int number) {
        Element itemElement = DomUtils.appendNewElement(oneOfElement, "item");
        DomUtils.appendNewText(itemElement, NUMBERS[number]);

        Element tagElement = DomUtils.appendNewElement(itemElement, "tag");
        DomUtils.appendNewText(tagElement, "number=" + number);
    }

    private NumberGrammarFactory() {}
}