package com.project.ptittoanthu.quiz.model;

public enum QuizSize {
    SHORT(10),
    MEDIUM(20),
    FULL(40);

    private final int numberOfQuestions;

    QuizSize(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }
}

