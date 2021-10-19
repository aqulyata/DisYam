package com.aqulasoft.disyam;

import com.aqulasoft.disyam.service.SettingsJob;
import com.aqulasoft.disyam.service.SettingsThread;
import org.apache.log4j.Logger;
import org.knowm.sundial.SundialJobScheduler;

import java.util.concurrent.TimeUnit;

public class Main {
    static Logger log = Logger.getLogger(Main.class);
    public static void main(final String[] args){
        final String token = args[0];
        final String username = args[1];
        final String password = args[2];
        new SettingsThread("settings").start();
        DisYamBot disYamBot = new DisYamBot(token, username, password);
        log.info("Bot created");
        disYamBot.Start();
    }
}
