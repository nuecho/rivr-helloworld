/*
 * Copyright (c) 2002-2013 Nu Echo Inc. All rights reserved.
 */

package com.nuecho.rivr.samples.helloworld;

import java.util.UUID;

import com.nuecho.rivr.voicexml.turn.output.audio.SynthesisText;
import com.nuecho.rivr.voicexml.turn.output.interaction.InteractionBuilder;
import com.nuecho.rivr.voicexml.turn.output.interaction.InteractionTurn;

/**
 * @author Nu Echo Inc.
 */
public final class FuentInteractionBuilder {
    InteractionBuilder mBuilder;

    private FuentInteractionBuilder(String id) {
        mBuilder = new InteractionBuilder(id);
    }

    static FuentInteractionBuilder newInteraction() {
        return newInteraction(UUID.randomUUID().toString());
    }

    static FuentInteractionBuilder newInteraction(String id) {
        return new FuentInteractionBuilder(id);
    }

    public FuentInteractionBuilder addPrompt(String synthesisPrompt) {
        mBuilder.addPrompt(new SynthesisText(synthesisPrompt));
        return this;
    }

    public InteractionTurn build() {
        return mBuilder.build();
    }
}
