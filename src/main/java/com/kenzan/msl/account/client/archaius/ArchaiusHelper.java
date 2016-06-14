package com.kenzan.msl.account.client.archaius;

public class ArchaiusHelper {

    public static void setupArchaius(){
        StringBuilder configPath = new StringBuilder();
        configPath.append("file://").append(System.getProperty("user.dir"));
        configPath.append("/../msl-account-data-client-config/data-client-config.properties");
        String additionalUrlsProperty = "archaius.configurationSource.additionalUrls";
        System.setProperty(additionalUrlsProperty, configPath.toString());
    }
}
