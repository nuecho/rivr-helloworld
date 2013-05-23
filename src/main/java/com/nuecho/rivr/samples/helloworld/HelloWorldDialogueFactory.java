/*
 * Copyright (c) 2012 Nu Echo Inc. All rights reserved.
 */

package com.nuecho.rivr.samples.helloworld;

import com.nuecho.rivr.core.dialogue.*;
import com.nuecho.rivr.voicexml.dialogue.*;
import com.nuecho.rivr.voicexml.rendering.voicexml.*;
import com.nuecho.rivr.voicexml.turn.input.*;
import com.nuecho.rivr.voicexml.turn.output.*;

/**
 * @author Nu Echo Inc.
 */
public class HelloWorldDialogueFactory implements VoiceXmlDialogueFactory {

    @Override
    public HelloWorldDialogue create(DialogueInitializationInfo<VoiceXmlInputTurn, VoiceXmlOutputTurn, VoiceXmlDialogueContext> dialogueInitializationInfo) {
        return new HelloWorldDialogue();
    }

}
