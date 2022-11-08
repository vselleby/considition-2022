package com.selleby;

import com.google.gson.Gson;
import com.selleby.models.IterationState;

import java.io.*;

public class RecordPersistor {
    private static final String FILE_PATH = "persistentData.json";
    private final PrintWriter printWriter;
    private final Gson gson = new Gson();

    private int highestScore = 0;

    public RecordPersistor() throws IOException {
        printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH, true)), true);
    }

    public synchronized void persist(IterationState iterationState) {
        if (scoreAbovePersistenceLevel(iterationState)) {
            String json = gson.toJson(iterationState);
            printWriter.println(json);
            highestScore = iterationState.submitResponse().score;
            System.out.println("New highscore saved: " + highestScore);
        }
    }

    private boolean scoreAbovePersistenceLevel(IterationState iterationState) {
        return iterationState.submitResponse().score > highestScore;
    }
}
