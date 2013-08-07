/*
 * Copyright (c) 2013 Nu Echo Inc. All rights reserved.
 */

package com.nuecho.rivr.samples.helloworld;

import javax.json.*;

import org.slf4j.*;

import com.nuecho.rivr.core.dialogue.*;
import com.nuecho.rivr.voicexml.dialogue.*;
import com.nuecho.rivr.voicexml.rendering.voicexml.*;
import com.nuecho.rivr.voicexml.turn.*;
import com.nuecho.rivr.voicexml.turn.first.*;
import com.nuecho.rivr.voicexml.turn.input.*;
import com.nuecho.rivr.voicexml.turn.last.*;
import com.nuecho.rivr.voicexml.turn.output.audio.*;
import com.nuecho.rivr.voicexml.turn.output.interaction.*;
import com.nuecho.rivr.voicexml.util.*;
import com.nuecho.rivr.voicexml.util.json.*;
import static com.nuecho.rivr.voicexml.turn.output.interaction.InteractionBuilder.*;
import static com.nuecho.rivr.voicexml.util.json.JsonUtils.*;
import static com.nuecho.rivr.voicexml.turn.input.VoiceXmlEvent.*;
/**
 * @author Nu Echo Inc.
 */
public final class HelloWorldDialogue implements VoiceXmlDialogue {

    private static final String DIALOG_ID_MDC_KEY = "dialogId";
    private static final String CAUSE_PROPERTY = "cause";
    private final Logger mDialogLog = LoggerFactory.getLogger("hello.world");

    @Override
    public VoiceXmlLastTurn run(VoiceXmlFirstTurn firstTurn, VoiceXmlDialogueContext context) throws Exception {

        MDC.put(DIALOG_ID_MDC_KEY, context.getDialogueId());

        mDialogLog.info("Starting dialogue");

        JsonValue cause = wrap("Normal");
        JsonObjectBuilder resultObjectBuilder = JsonUtils.createObjectBuilder();
        try {
            InteractionTurn turn = newInteractionBuilder("hello").addPrompt(new SynthesisText("Hello World!")).build();
            VoiceXmlInputTurn inputTurn = DialogueUtils.doTurn(context, turn);
            if (hasEvent(CONNECTION_DISCONNECT_HANGUP, inputTurn.getEvents()))
                cause = wrap("Hangup");
            if (hasEvent(ERROR, inputTurn.getEvents()))
                cause = wrap(inputTurn.getEvents().get(0).getMessage());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            cause = wrap("Interupted");
        } catch (Exception exception) {
            mDialogLog.error("Error during dialogue", exception);
            cause = ResultUtils.toJson(exception);
        }
        resultObjectBuilder.add(CAUSE_PROPERTY, cause);
        VariableDeclarationList variables = VariableDeclarationList.create(resultObjectBuilder.build());
        mDialogLog.info("Ending dialogue");

        return new VoiceXmlExitTurn("result", variables);
    }
}