/*
 * Copyright (c) 2013 Nu Echo Inc. All rights reserved.
 */

package com.nuecho.rivr.samples.helloworld;

import com.nuecho.rivr.core.servlet.*;
import com.nuecho.rivr.voicexml.turn.last.*;

/**
 * @author Nu Echo Inc.
 */
public class HelloWorldErrorHandler implements ErrorHandler<VoiceXmlLastTurn> {
    @Override
    public VoiceXmlLastTurn handleError(Throwable error) {
        return new VoiceXmlReturnTurn("error", "com.nuecho.rivr.error", null);
    }
}
