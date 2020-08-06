package fr.catcore.translated.server.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class ConfigManager {

    private static Config config;

    private static final File configFile;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String getLanguageCodeFromConfig() {
        if (!configFile.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(configFile);
                config = new Config();
                fileWriter.write(GSON.toJson(config));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileReader fileReader = new FileReader(configFile);
                config = GSON.fromJson(fileReader, Config.class);
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return config.getLanguageCode();
    }


    public static class Config {

        private final String language_code;

        public Config(String language_code) {
            this.language_code = language_code;
        }

        public Config() {
            this("en_us");
        }

        public String getLanguageCode() {
            return language_code;
        }
    }

    static {
        File config1;
        try {
            config1 = new File(FabricLoader.getInstance().getConfigDir().toFile(), "translated_server.json");
        } catch (NoSuchMethodError error) {
            config1 = new File(FabricLoader.getInstance().getConfigDirectory(), "translated_server.json");
        }
        configFile = config1;
    }
}
