package splitter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Storage {

    private static final String FILE_PATH = "data/group.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void save(Group group) {
        try {
            new File("data").mkdirs();
            FileWriter writer = new FileWriter(FILE_PATH);
            gson.toJson(group, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public static Group load() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return new Group();
        }

        try {
            FileReader reader = new FileReader(file);
            Group group = gson.fromJson(reader, Group.class);
            reader.close();

            if (group == null) return new Group();
            if (group.people == null) group.people = new java.util.ArrayList<>();
            if (group.expenses == null) group.expenses = new java.util.ArrayList<>();

            return group;
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
            return new Group();
        }
    }
}
