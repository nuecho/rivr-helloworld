/*
 * Copyright (c) 2013 Nu Echo Inc. All rights reserved.
 */

package com.nuecho.rivr.samples.helloworld;

import org.junit.*;
import org.slf4j.*;

import com.nuecho.rivr.core.channel.synchronous.step.*;
import com.nuecho.rivr.core.util.*;
import com.nuecho.rivr.voicexml.dialogue.*;
import com.nuecho.rivr.voicexml.test.*;
import com.nuecho.rivr.voicexml.turn.first.*;
import com.nuecho.rivr.voicexml.turn.last.*;
import com.nuecho.rivr.voicexml.turn.output.*;
import com.nuecho.rivr.voicexml.util.json.*;

/**
 * @author Nu Echo Inc.
 */
public class DialogueTests {

    private VoiceXmlTestDialogueChannel mDialogueChannel;

    @Before
    public void init() {
        mDialogueChannel = new VoiceXmlTestDialogueChannel("Dialog Tests", Duration.seconds(5));
    }

    @Test
    public void test() {
        mDialogueChannel.dumpLogs();
        startDialogue(new VoiceXmlFirstTurn());

        mDialogueChannel.processValue(JsonUtils.createObjectBuilder()
                                               .add("clid", "5145551234")
                                               .add("dnis", "5551234")
                                               .build());

    }

    private Step<VoiceXmlOutputTurn, VoiceXmlLastTurn> startDialogue(VoiceXmlFirstTurn firstTurn) {
        HelloWorldDialogue dialogue = new HelloWorldDialogue();
        VoiceXmlDialogueContext context = new VoiceXmlDialogueContext(mDialogueChannel,
                                                                      LoggerFactory.getLogger(getClass()),
                                                                      "x",
                                                                      "contextPath",
                                                                      "servletPath");
        return mDialogueChannel.startDialogue(dialogue, firstTurn, context);
    }

    @After
    public void terminate() {
        mDialogueChannel.dispose();
    }

}
