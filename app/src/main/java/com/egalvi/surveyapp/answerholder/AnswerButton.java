package com.egalvi.surveyapp.answerholder;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

import ru.egalvi.survey.model.Answer;

public class AnswerButton extends AppCompatButton implements HasAnswer {
    private Answer answer;

    public AnswerButton(Context context, Answer answer) {
        super(context);
        this.answer = answer;
        this.setText(answer.getText());
    }

    public Answer getAnswer() {
        return answer;
    }
}
