package com.egalvi.surveyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egalvi.surveyapp.answerholder.AnswerButton;

import ru.egalvi.survey.model.Answer;
import ru.egalvi.survey.model.Question;
import ru.egalvi.survey.service.impl.SurveyIterationHandlerImpl;
import ru.egalvi.survey.service.impl.SurveyServiceImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        try {
            SurveyServiceImpl service = new SurveyServiceImpl(getAssets().open("test.xml"));
            final SurveyIterationHandlerImpl survey = service.getSurvey();

            handleQuestion(survey);
        } catch (Exception e) {
            //TODO!!!
            e.printStackTrace();
        }
    }

    private void handleQuestion(final SurveyIterationHandlerImpl survey) {
        TextView questionText = (TextView) MainActivity.this.findViewById(R.id.questionText);
        LinearLayout answers = (LinearLayout) MainActivity.this.findViewById(R.id.answerContainer);
        if (survey.hasNestQuestion()) {
            Question question = survey.getNextQuestion();
            questionText.setText(question.getText());
            answers.removeAllViews();
            for (Answer a : question.getAnswer()) {
                final AnswerButton answer = new AnswerButton(MainActivity.this, a);
                answers.addView(answer);
                answer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        survey.setAnswer(answer.getAnswer());
                        handleQuestion(survey);
                    }
                });
            }
        } else {
            questionText.setText(survey.getResult());
            answers.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
