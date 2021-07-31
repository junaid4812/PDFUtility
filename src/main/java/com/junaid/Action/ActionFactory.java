package com.junaid.Action;

import com.junaid.util.PDFConstants;
import org.apache.commons.cli.CommandLine;

import java.util.Locale;

import static com.junaid.util.PDFConstants.*;

public class ActionFactory {

    public Action getAction(final CommandLine cmd) {
        String actionType = cmd.getOptionValue(PDFConstants.PARAM_ACTION);
        String actionValue = actionType.toUpperCase(Locale.ROOT);
        Action action;
        switch (actionValue) {
            case ACTION_MERGE:
                action = new MergeAction(cmd);
                break;
            case ACTION_EXTRACT:
                action = new ExtractAction(cmd);
                break;
            case ACTION_SPLIT:
                action = new SplitAction(cmd);
                break;
            default:
                throw new RuntimeException(String.format(PDFConstants.ERR_INVALID_ACTION, actionType,
                        ACTION_MERGE, ACTION_SPLIT, ACTION_EXTRACT));
        }
        return action;
    }
}
