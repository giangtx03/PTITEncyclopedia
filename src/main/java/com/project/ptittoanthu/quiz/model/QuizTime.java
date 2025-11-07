package com.project.ptittoanthu.quiz.model;

public enum QuizTime {
    NINETY(90), FORTY_FIVE(45), UNLIMIT(-1), FIFTEEN(15);

    public final int time;

    QuizTime(int time) {
        this.time = time;
    }
}
