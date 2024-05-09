package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import data.Dragon;
import main.App;
import utility.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Класс для работы с файлом
 *
 * @author steepikk
 */
public class DumpManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    private final String fileName;

    public DumpManager(String fileName) {
        if (!(new File(fileName).exists())) {
            fileName = "../" + fileName;
        }
        this.fileName = fileName;
    }

    /**
     * Записывает коллекцию в файл.
     *
     * @param collection коллекция
     */
    public void writeCollection(Collection<Dragon> collection) {
        try (BufferedWriter collectionBufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            collectionBufferedWriter.write(gson.toJson(collection));
            App.logger.info("The collection has been successfully saved to a file!");
        } catch (IOException exception) {
            App.logger.info("This file cannot be opened!");
        }
    }

    /**
     * Считывает коллекцию из файла.
     *
     * @return Считанная коллекция
     */
    public Collection<Dragon> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = new TypeToken<HashSet<Dragon>>() {
                }.getType();
                var reader = new BufferedReader(fileReader);

                var jsonString = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        jsonString.append(line);
                    }
                }

                if (jsonString.length() == 0) {
                    jsonString = new StringBuilder("[]");
                }

                HashSet<Dragon> collection = gson.fromJson(jsonString.toString(), collectionType);

                App.logger.info("The collection has been uploaded successfully!");
                return collection;

            } catch (FileNotFoundException exception) {
                App.logger.info("The boot file was not found!");
            } catch (NoSuchElementException exception) {
                App.logger.info("The boot file is empty!");
            } catch (JsonParseException exception) {
                App.logger.info("The required collection was not found in the download file!");
            } catch (IllegalStateException | IOException exception) {
                App.logger.info("An unexpected error!");
                System.exit(0);
            }
        } else {
            App.logger.info("The command line argument with the boot file was not found!");
        }
        return new HashSet<>();
    }
}
