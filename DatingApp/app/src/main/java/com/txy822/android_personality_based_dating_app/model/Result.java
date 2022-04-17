package com.txy822.android_personality_based_dating_app.model;

/**
 * Result class An immutable result returned by a TextClassifier describing what was classified.
 * */
public class Result implements Comparable<Result> {
    /**
     * A unique identifier for what has been classified.
     * Specific to the class, not the instance of the object.
     */
    private final String id;

    /** Display name for the result.
     */
    private final String title;

    /** A sortable score for how good the result is relative to others.
     *  Higher should be better.
     */
    private final Float confidence;

    public Result(final String id, final String title, final Float confidence) {
        this.id = id;
        this.title = title;
        this.confidence = confidence;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Float getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {
        String resultString = "";
        if (id != null) {
            resultString += "[" + id + "] ";
        }

        if (title != null) {
            resultString += title + " ";
        }

        if (confidence != null) {
            resultString += String.format("(%.1f%%) ", confidence * 100.0f);
        }

        return resultString.trim();
    }

    @Override
    public int compareTo(Result o) {
        return o.confidence.compareTo(confidence);
    }
}