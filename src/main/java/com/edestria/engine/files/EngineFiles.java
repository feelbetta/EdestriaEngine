package com.edestria.engine.files;

import com.edestria.engine.EdestriaEngine;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class EngineFiles {

    private static final String SEPARATOR = File.separator;

    private final EdestriaEngine edestriaEngine;

    public EngineFiles(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
        File properties = new File(this.edestriaEngine.getDataFolder() + File.separator + "settings.properties");
        if (properties.exists()) {
            return;
        }
        properties.getParentFile().mkdirs();
    }

    public Object getProperty(String fileName, String property) {
        File path = new File(this.edestriaEngine.getDataFolder() + File.separator);
        if (path.listFiles() == null) {
            return null;
        }
        try {
            File file = Files.walk(Paths.get(path.toURI())).filter(fp -> this.stripFileExtension(fp.toFile()).equalsIgnoreCase(fileName)).map(Path::toFile).findFirst().orElse(null);
            if (file == null) {
                return null;
            }
            if (this.getFileExtension(file).equals("yml")) {
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                return fileConfiguration.get(property);
            }
            if (this.getFileExtension(file).equals("properties")) {
                Properties properties = new Properties();
                properties.load(new FileInputStream(fileName + ".properties"));
                return properties.get(property);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String getStringProperty(String fileName, String property) {
        return this.getProperty(fileName, property) == null ? "" : (String) this.getProperty(fileName, property);
    }

    public int getIntegerProperty(String fileName, String property) {
        return this.getProperty(fileName, property) == null ? 0 : (int) this.getProperty(fileName, property);
    }

    public List<String> getStringListProperty(String fileName, String property) {
        return this.getProperty(fileName, property) == null ? new ArrayList<>() : Arrays.stream(this.getStringProperty(fileName, property).split(",")).collect(Collectors.toList());
    }

    private String getFileExtension(File file) {
        return com.google.common.io.Files.getFileExtension(file.getName());
    }

    private String stripFileExtension(File file) {
        return com.google.common.io.Files.getNameWithoutExtension(file.getName());
    }
}
