package com.egalvi.surveyapp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.egalvi.survey.model.Answer;
import ru.egalvi.survey.model.Question;
import ru.egalvi.survey.service.impl.SurveyIterationHandlerImpl;
import ru.egalvi.survey.service.impl.SurveyServiceImpl;

public class MainActivity extends AppCompatActivity {

    private Answer checkedAnswer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try {
            SurveyServiceImpl service = new SurveyServiceImpl(getAssets().open("test.xml"));
            final SurveyIterationHandlerImpl survey = service.getSurvey();

            final Button next = (Button) findViewById(R.id.forward);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkedAnswer != null) {
                        survey.setAnswer(checkedAnswer);
                    }
                    TextView questionText = (TextView) MainActivity.this.findViewById(R.id.questionText);
                    RadioGroup answers = (RadioGroup) MainActivity.this.findViewById(R.id.answerContainer);
                    if (survey.hasNestQuestion()) {
                        Question question = survey.getNextQuestion();
                        questionText.setText(question.getText());
                        next.setText(survey.hasNestQuestion() ? "NEXT" : "RESULT");
                        answers.removeAllViews();
                        for (Answer a : question.getAnswer()) {
                            RadioButton newRadioButton = new AnswerRadioButton(MainActivity.this, a);
                            newRadioButton.setText(a.getText());
                            answers.addView(newRadioButton);
                        }
                    } else {
                        questionText.setText(survey.getResult());
                        answers.setVisibility(View.GONE);
                    }
                }
            });

            final RadioGroup answers = (RadioGroup) MainActivity.this.findViewById(R.id.answerContainer);
            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId) {
                        case -1:
                            checkedAnswer = null;
//                            Toast.makeText(getApplicationContext(), "Ничего не выбрано",
//                                    Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            HasAnswer checkedRB = (HasAnswer) findViewById(checkedId);
//                            Toast.makeText(getApplicationContext(), "Выбран переключатель " + checkedId + " " + checkedRB.getAnswer().getText(),
//                                    Toast.LENGTH_SHORT).show();
                            checkedAnswer = checkedRB.getAnswer();
                            break;
                    }
                }
            });
        } catch (Exception e) {
            //TODO!!!
            e.printStackTrace();
        }


//        while (survey.hasNestQuestion()) {
//            Question question = survey.getNextQuestion();
//            System.out.println(question.getText());
//            List<Answer> answers = question.getAnswer();
//            int i = 0;
//            for (Answer a : answers) {
//                System.out.println(++i + ". " + a.getText());
//            }
//
//            Integer answerNumber = null;
//            while (answerNumber == null) {
//                System.out.print("Enter answer number:");
//                try {
//                    answerNumber = Integer.parseInt(br.readLine());
//                    if (answerNumber > answers.size() || answerNumber <= 0) {
//                        answerNumber = null;
//                        System.err.println("Invalid input");
//                    }
//                } catch (NumberFormatException nfe) {
//                    System.err.println("Invalid Format!");
//                }
//            }
////            answerNumber = aI.next();
//
//            survey.setAnswer(answers.get(answerNumber - 1));
//        }
//        System.out.println(survey.getResult());
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
