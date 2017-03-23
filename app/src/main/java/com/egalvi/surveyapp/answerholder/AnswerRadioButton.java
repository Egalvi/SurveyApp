package com.egalvi.surveyapp.answerholder;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;

import ru.egalvi.survey.model.Answer;

public class AnswerRadioButton extends AppCompatRadioButton implements HasAnswer {
    private Answer answer;

    public AnswerRadioButton(Context context, Answer answer) {
        super(context);
        this.answer = answer;
        this.setText(answer.getText());
    }

    public Answer getAnswer() {
        return answer;
    }
}
