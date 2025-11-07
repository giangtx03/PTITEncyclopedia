package com.project.ptittoanthu.quiz.model;

public enum QuizType {
    EXAM(0), PRACTICE(1);

    public final int value;

    QuizType(int value) {
        this.value = value;
    }
}
