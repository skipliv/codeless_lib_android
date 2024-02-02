/*
 **********************************************************************************
 *
 * SPDX-License-Identifier: BSD-3-Clause
 *
 * Copyright (c) 2020-2024 Renesas Electronics Corporation and/or its affiliates
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of Renesas nor the names of its contributors may be
 *    used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY RENESAS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, NONINFRINGEMENT, AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL RENESAS OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 **********************************************************************************
 */

package com.diasemi.codelesslib.command;

import com.diasemi.codelesslib.CodelessManager;
import com.diasemi.codelesslib.CodelessProfile;
import com.diasemi.codelesslib.CodelessProfile.Command;
import com.diasemi.codelesslib.CodelessProfile.CommandID;

import java.util.regex.Pattern;

/**
 * <code>ATF</code> command implementation.
 * @see <a href="https://lpccs-docs.renesas.com/UM-140-DA145x-CodeLess/index.html">AT commands documentation</a>
 */
public class ErrorReportingCommand extends CodelessCommand {
    public static final String TAG = "ErrorReportingCommand";

    public static final String COMMAND = "F";
    public static final String NAME = CodelessProfile.PREFIX + COMMAND;
    public static final CommandID ID = CommandID.ATF;

    public static final String PATTERN_STRING = "^F=(\\d)$"; // <enabled>
    public static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);

    /** The error reporting configuration argument (<code>true</code> for on, <code>false</code> for off). */
    private boolean enabled;

    /**
     * Creates an <code>ATF</code> command.
     * @param manager   the associated manager
     * @param enabled   the error reporting configuration argument (<code>true</code> for on, <code>false</code> for off)
     */
    public ErrorReportingCommand(CodelessManager manager, boolean enabled) {
        this(manager);
        this.enabled = enabled;
    }

    /**
     * Default constructor.
     * @see CodelessCommand#CodelessCommand(CodelessManager)
     */
    public ErrorReportingCommand(CodelessManager manager) {
        super(manager);
    }

    /**
     * Default constructor.
     * @see CodelessCommand#CodelessCommand(CodelessManager, String, boolean)
     */
    public ErrorReportingCommand(CodelessManager manager, String command, boolean parse) {
        super(manager, command, parse);
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    public String getID() {
        return COMMAND;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public CommandID getCommandID() {
        return ID;
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    protected boolean hasArguments() {
        return true;
    }

    @Override
    protected String getArguments() {
        return Integer.toString(enabled ? Command.ERROR_REPORTING_ON : Command.ERROR_REPORTING_OFF);
    }

    @Override
    protected boolean requiresArguments() {
        return true;
    }

    @Override
    protected boolean checkArgumentsCount() {
        return CodelessProfile.countArguments(command, ",") == 1;
    }

    @Override
    protected String parseArguments() {
        Integer num = decodeNumberArgument(1);
        if (num == null || num != Command.ERROR_REPORTING_ON && num != Command.ERROR_REPORTING_OFF)
            return "Argument must be 0 or 1";
        enabled = num == Command.ERROR_REPORTING_ON;
        return null;
    }

    /** Returns the error reporting configuration argument (<code>true</code> for on, <code>false</code> for off). */
    public boolean isEnabled() {
        return enabled;
    }

    /** Sets the error reporting configuration argument (<code>true</code> for on, <code>false</code> for off). */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
