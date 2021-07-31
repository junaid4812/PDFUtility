package com.junaid;

import com.junaid.Action.Action;
import com.junaid.Action.ActionFactory;
import com.junaid.model.Parameters;
import com.junaid.util.PDFUtil;
import org.apache.commons.cli.CommandLine;


public class PDFUtility {
    public static void main(String... args) {
        try {
            PDFUtil.clearConsole();
            PDFUtil.welcomeMessage();
            Parameters param = new Parameters();
            CommandLine cmd = param.readInputParameters(args);
            ActionFactory factory = new ActionFactory();
            Action action = factory.getAction(cmd);
            action.performAction();

        } catch (Exception e) {
            System.exit(1);
            return;
        }


    }
}
