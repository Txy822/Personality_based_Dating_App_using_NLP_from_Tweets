package com.txy822.android_personality_based_dating_app.model;

import android.content.Context;
import android.util.Log;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.core.BaseOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier;


public class BertClassifier {
    private static final String TAG = "TaskApi";
    /**
     * Tensorflow lite converted model to classify personality from tweets
     */
    private static final String MODEL_PATH = "model.tflite";
    private final Context context;
    BertNLClassifier.BertNLClassifierOptions options;
    BertNLClassifier classifier;


    /**
     * Constructor
     * @param context context context
     */
    public BertClassifier(Context context) {
        this.context = context;
    }

    /**
     * loads the tensorflow natural language classifier
     */
    public void load() {
        try {
            // Initialization
            options =
                    BertNLClassifier.BertNLClassifierOptions.builder()
                            .setBaseOptions(BaseOptions.builder().setNumThreads(4).build())
                            .build();
            classifier = BertNLClassifier.createFromFile(context, MODEL_PATH);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());

        }
    }
    public void unload() {
        classifier.close();
        classifier = null;
    }

    /**
     * Classifies the given input processed text and gives the probabilistic output
     * @param text String input text
     * @return List of Results
     */
    public List<Result> classify(String text) {

        List<Category> apiResults = classifier.classify(text);

        List<Result> results = new ArrayList<>(apiResults.size());

        for (int i = 0; i < apiResults.size(); i++) {
            Category category = apiResults.get(i);
            results.add(new Result("" + i, category.getLabel(), category.getScore()));
        }
        Collections.sort(results);
        return results;
    }
}
