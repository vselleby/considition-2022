package com.selleby;

import com.google.gson.Gson;
import com.selleby.models.IterationState;

import java.io.*;

public class RecordPersistor {
    private static final int FANCY_VILLE_MINIMUM_SCORE = 14500;
    private static final int SUBURBIA_MINIMUM_SCORE = 1150;
    private static final String FILE_PATH = "persistentData.json";
    private final PrintWriter printWriter;
    private final Gson gson = new Gson();

    public RecordPersistor() throws IOException {
        printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH, true)), true);
    }

    public synchronized void persist(IterationState iterationState) {
        if (scoreAbovePersistenceLevel(iterationState)) {
            String json = gson.toJson(iterationState);
            printWriter.println(json);
        }
    }

    private boolean scoreAbovePersistenceLevel(IterationState iterationState) {
        if (iterationState.solution().mapName.equals("Fancyville")) {
            return iterationState.submitResponse().score >= FANCY_VILLE_MINIMUM_SCORE;
        }
        else {
            return iterationState.submitResponse().score >= SUBURBIA_MINIMUM_SCORE;
        }
    }
}
