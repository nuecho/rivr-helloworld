/*
 * Copyright (c) 2013 Nu Echo Inc. All rights reserved.
 */

package com.nuecho.rivr.samples.helloworld;

import javax.json.*;

import org.slf4j.*;

import com.nuecho.rivr.core.channel.*;
import com.nuecho.rivr.core.dialogue.*;
import com.nuecho.rivr.voicexml.dialogue.*;
import com.nuecho.rivr.voicexml.rendering.voicexml.*;
import com.nuecho.rivr.voicexml.turn.*;
import com.nuecho.rivr.voicexml.turn.first.*;
import com.nuecho.rivr.voicexml.turn.input.*;
import com.nuecho.rivr.voicexml.turn.last.*;
import com.nuecho.rivr.voicexml.turn.output.*;
import com.nuecho.rivr.voicexml.turn.output.audio.*;
import com.nuecho.rivr.voicexml.turn.output.interaction.*;
import com.nuecho.rivr.voicexml.util.*;
import com.nuecho.rivr.voicexml.util.json.*;

/**
 * @author Nu Echo Inc.
 */
public final class HelloWorldDialogue implements VoiceXmlDialogue {

    private final Logger mLog = LoggerFactory.getLogger(getClass());

    private static final String STATUS_PROPERTY = "status";
    private static final String STATUS_ERROR = "error";
    private static final String STATUS_INTERRUPTED = "interrupted";
    private static final String STATUS_SUCCESS = "success";

    private static final String CAUSE_PROPERTY = "cause";

    private static final String VICTORY_MESSAGE_RECORDING_LOCATION = "application.victoryMessage";

    @Override
    public VoiceXmlLastTurn run(VoiceXmlFirstTurn firstTurn, VoiceXmlDialogueContext context) throws Exception {

        extractClidAndDnis(context);

        String status;
        JsonObjectBuilder resultObjectBuilder = JsonUtils.createObjectBuilder();
        try {
            mLog.info("Starting dialogue " + context.getDialogueId());
            playHelloWorldMessage(context);
            status = STATUS_SUCCESS;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            status = STATUS_INTERRUPTED;
        } catch (Exception exception) {
            mLog.error("Error during dialogue", exception);
            status = STATUS_ERROR;
            JsonUtils.add(resultObjectBuilder, CAUSE_PROPERTY, ResultUtils.toJson(exception));
            return new VoiceXmlReturnTurn(STATUS_ERROR, "com.nuecho.rivr", null);
        }

        JsonUtils.add(resultObjectBuilder, STATUS_PROPERTY, status);
        VariableDeclarationList variables = VariableDeclarationList.create(resultObjectBuilder.build());
        variables.addVariable(new VariableDeclaration("recording", VICTORY_MESSAGE_RECORDING_LOCATION));

        mLog.info("Ending dialogue " + context.getDialogueId());
        return new VoiceXmlReturnTurn("result", variables);
    }

    private void extractClidAndDnis(VoiceXmlDialogueContext context) throws Timeout, InterruptedException, HangUp,
            PlatformError {
        ScriptExecutionTurn clidAndDnisTurn = new ScriptExecutionTurn("clidAndDnis");
        VariableDeclarationList clidAndDnisVariables = new VariableDeclarationList();
        clidAndDnisVariables.addVariable(new VariableDeclaration("clid", "session.connection.remote.uri"));
        clidAndDnisVariables.addVariable(new VariableDeclaration("dnis", "session.connection.local.uri"));
        clidAndDnisTurn.setVariables(clidAndDnisVariables);

        VoiceXmlInputTurn inputTurn = processTurn(clidAndDnisTurn, context);
        JsonObject result = (JsonObject) inputTurn.getJsonValue();

        if (result != null) {
            String clid = result.getString("clid");
            String dnis = result.getString("dnis");
            context.getLogger().info("CLID: {}, DNIS: {}", clid, dnis);
        }

    }

    private VoiceXmlInputTurn processTurn(VoiceXmlOutputTurn outputTurn, VoiceXmlDialogueContext context)
            throws Timeout, InterruptedException, HangUp, PlatformError {
        VoiceXmlInputTurn inputTurn = DialogueUtils.doTurn(context, outputTurn);

        if (VoiceXmlEvent.hasEvent(VoiceXmlEvent.CONNECTION_DISCONNECT_HANGUP, inputTurn.getEvents()))
            throw new HangUp();

        if (VoiceXmlEvent.hasEvent(VoiceXmlEvent.ERROR, inputTurn.getEvents()))
            throw new PlatformError(inputTurn.getEvents().get(0).getMessage());

        return inputTurn;
    }

    private void playHelloWorldMessage(VoiceXmlDialogueContext context) throws Timeout, InterruptedException, HangUp,
            PlatformError {
        InteractionBuilder builder = new InteractionBuilder("playMessage");
        builder.addPrompt(new SynthesisText("Hello World!"));
        processTurn(builder.build(), context);
    }

}