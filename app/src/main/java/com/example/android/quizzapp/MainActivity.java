
// Please note: Marvel images and all its related characters belong to Marvel Comics and Marvel Studios.

package com.example.android.quizzapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Global variable for the total score of the entire quiz
    int totalscore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If the phone is in landscape mode, the 'marvel • cinematic • universe' text will be stretched
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            TextView mcuHeading = (TextView) findViewById(R.id.marvel_cinematic_universe_text_view);
            mcuHeading.setTextScaleX((float) 1.6);
        }

        // Portrait Mode
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            // TextView for question 6 uses the alternative text in the strings.xml when in portrait mode
            TextView q6TextViewNextLine = (TextView) findViewById(R.id.question_6_text_view);
            q6TextViewNextLine.setText(getString(R.string.question6_linebreak));
        }
    }

    /**
     * This method is called when the Submit button is clicked
     *
     * @param view if everything is correctly completed a toast message appears with the user's final score
     *             and if the user got less then 10 as a score, the score and all questions that were
     *             incorrectly answered are shown in the toast message
     */
    public void submitAnswers(View view) {

        RadioGroup checkQ1RadioButtons = (RadioGroup) findViewById(R.id.q1_radio_group);
        RadioGroup checkQ2RadioButtons = (RadioGroup) findViewById(R.id.q2_radio_group);
        RadioGroup checkQ3RadioButtons = (RadioGroup) findViewById(R.id.q3_radio_group);

        //Check if the user selected the Iron Man radio button for question 1
        RadioButton ironManRadioButton = (RadioButton) findViewById(R.id.iron_man_radio);
        boolean choseIronMan = ironManRadioButton.isChecked();

        //Check if the user selected the Avengers radio button for question 2
        RadioButton avengerRadioButton = (RadioButton) findViewById(R.id.avengers_radio_button);
        boolean choseAvengers = avengerRadioButton.isChecked();

        //Check if the user selected the Thanos radio button for question 3
        RadioButton thanosRadioButton = (RadioButton) findViewById(R.id.thanos_radio_button);
        boolean choseThanos = thanosRadioButton.isChecked();

        //User checks Nick Fury for question 4
        CheckBox nickFuryCheckBox = (CheckBox) findViewById(R.id.nick_fury_check_box);
        boolean checkedNickFury = nickFuryCheckBox.isChecked();

        //User checks Stan Lee for question 4
        CheckBox stanLeeCheckBox = (CheckBox) findViewById(R.id.stan_lee_check_box);
        boolean checkedStanLee = stanLeeCheckBox.isChecked();

        //User checks Natasha Romanoff for question 4
        CheckBox natashaCheckBox = (CheckBox) findViewById(R.id.natasha_check_box);
        boolean checkedNatasha = natashaCheckBox.isChecked();

        //User checks Sam Wilson for question 4
        CheckBox samWilsonCheckBox = (CheckBox) findViewById(R.id.sam_wilson_check_box);
        boolean checkedSamWilson = samWilsonCheckBox.isChecked();

        // User enters Dr Strange or some other text for question 5
        EditText drStrangeEditTextView = (EditText) findViewById(R.id.dr_strange_edit_text);
        String drStrange = drStrangeEditTextView.getText().toString().trim();

        // User enters Black Panther or some other text for question 6
        EditText blackpantherEditTextView = (EditText) findViewById(R.id.black_panther_edit_text);
        String blackPanther = blackpantherEditTextView.getText().toString().trim();


        //The If statements check that all questions are answered, then checks if the list of check boxes has only two checks (question 4)
        if (checkQ1RadioButtons.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Question 1 not answered.", Toast.LENGTH_LONG).show();
        } else if (checkQ2RadioButtons.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Question 2 not answered.", Toast.LENGTH_LONG).show();
        } else if (checkQ3RadioButtons.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Question 3 not answered.", Toast.LENGTH_LONG).show();
        } else if (!checkedNickFury && !checkedStanLee && !checkedNatasha && !checkedSamWilson) {
            Toast.makeText(this, "Question 4 not answered.", Toast.LENGTH_LONG).show();
        } else if (drStrange.equals("")) {
            Toast.makeText(this, "Question 5 not answered.", Toast.LENGTH_LONG).show();
        } else if (blackPanther.equals("")) {
            Toast.makeText(this, "Question 6 not answered.", Toast.LENGTH_LONG).show();
        } else if (nickFuryCheckBox.isChecked() || stanLeeCheckBox.isChecked() || natashaCheckBox.isChecked() || samWilsonCheckBox.isChecked()) {

            //checkNrOfCheckboxesChecked method checks how many checkboxes were checked
            int finalNrOfCheckboxes = checkNrOfCheckboxesChecked(nickFuryCheckBox, stanLeeCheckBox, natashaCheckBox, samWilsonCheckBox);

            //If the user checked only 1 checkbox, give an error toast message
            if (finalNrOfCheckboxes < 2) {
                Toast.makeText(this, "Question 4 must have TWO selected answers", Toast.LENGTH_LONG).show();
            } else if (finalNrOfCheckboxes > 2) {

                // if the user checked more than 2 checkboxes, give an error toast message
                Toast.makeText(this, "Question 4 cannot have more than TWO selected answers", Toast.LENGTH_LONG).show();

            } else {

                //The calculateScoreFromRadioButtons method calculates the points from the selected radio buttons
                int finalRadioScore = calculateScoreFromRadioButtons(choseIronMan, choseAvengers, choseThanos);

                //The calculateScoreFromCheckBoxes method calculates the points from the checked checkboxes
                int finalCheckScore = calculateScoreFromCheckBoxes(checkedNickFury, checkedStanLee);

                //The calculateScoreFromEditText method calculates the points from the Edit Views
                int finalEditScore = calculateScoreFromEditText(drStrange, blackPanther);

                //Calculate final score
                totalscore = finalRadioScore + finalCheckScore + finalEditScore;

                if (totalscore == 10) {
                    //You get this toast message if the score is equal to 10
                    Toast.makeText(this, "Congratulations." + "\nYour score is " + totalscore + "/10", Toast.LENGTH_LONG).show();

                    //Clear all the answers to the questions
                    resetQuestions(nickFuryCheckBox, stanLeeCheckBox, natashaCheckBox, samWilsonCheckBox//
                            , drStrangeEditTextView, blackpantherEditTextView);

                } else {

                    //scoreMessage stores the text returned from the lessThenFullScoreMessage method
                    String scoreMessage =  getLessThenFullScoreMessage(choseIronMan, choseAvengers, choseThanos,
                            checkedNickFury, checkedStanLee, checkedNatasha, checkedSamWilson, finalEditScore);

                    //You get this toast message if the score is less then 10
                    Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show();

                    //Clear all the answers to the questions
                    resetQuestions(nickFuryCheckBox, stanLeeCheckBox, natashaCheckBox, samWilsonCheckBox//
                            , drStrangeEditTextView, blackpantherEditTextView);
                }
            }
        }
    }

    /**
     * This method checks how many check boxes were checked for question 4
     *
     * @param nickFuryCheckBox  did the user check the Nick Fury checkbox?
     * @param stanLeeCheckBox   did the user check the Stan Lee checkbox?
     * @param natashaCheckBox   did the user check the Natasha Romanoff checkbox?
     * @param samWilsonCheckBox did the user check the Nick Fury checkbox?
     * @return checkNoOfCheckboxes variable to determine how many check boxes were checked
     */
    private int checkNrOfCheckboxesChecked(CheckBox nickFuryCheckBox, CheckBox stanLeeCheckBox, CheckBox natashaCheckBox, CheckBox samWilsonCheckBox) {

        //Adds one for every  check box checked
        int checkNoOfCheckboxes = 0;

        if (nickFuryCheckBox.isChecked()) {
            checkNoOfCheckboxes += 1;
        }

        if (stanLeeCheckBox.isChecked()) {
            checkNoOfCheckboxes += 1;
        }

        if (natashaCheckBox.isChecked()) {
            checkNoOfCheckboxes += 1;
        }

        if (samWilsonCheckBox.isChecked()) {
            checkNoOfCheckboxes += 1;
        }

        //Returns the total number of checkboxes checked
        return checkNoOfCheckboxes;
    }

    /**
     * This method calculates the final score based on the radio buttons selected
     *
     * @param choseIronMan  the user selected the Iron Man radiobutton
     * @param choseAvengers the user selected the Avengers radiobutton
     * @param choseThanos   the user selected the Thanos radiobutton
     * @return the final score of the correct, selected radio buttons
     */
    private int calculateScoreFromRadioButtons(boolean choseIronMan, boolean choseAvengers, boolean choseThanos) {

        int radioScore = 0;

        // If the user chose Iron Man, score increases by one
        if (choseIronMan) {
            radioScore += 1;
        }

        //If the user selects The Avengers, score increases by one
        if (choseAvengers) {
            radioScore += 1;
        }

        //If the user selects Thanos, score increases by one
        if (choseThanos) {
            radioScore += 1;
        }
        return radioScore;
    }

    /**
     * This method calculates the final score for all the correct checked checkboxes
     *
     * @param checkedNickFury the user checked the Nick Fury checkbox
     * @param checkedStanLee  the user checked the Stan Lee checkbox
     * @return the final score for the correct, checked checkboxes
     */
    private int calculateScoreFromCheckBoxes(boolean checkedNickFury, boolean checkedStanLee) {

        int checkScore = 0;

        //User checks Nick Fury, score increases by one
        if (checkedNickFury) {
            checkScore += 1;
        }

        //User checks Stan Lee, score increases by one
        if (checkedStanLee) {
            checkScore += 1;
        }
        return checkScore;
    }

    /**
     * This method calculates the final score for the Edit TextViews and compares the text entered
     * into the Edit TextViews to the correct answers (questions 5 and 6)
     *
     * @param drStrange    the user typed Dr Strange as the answer
     * @param blackPanther the user typed Black Panther as the answer
     * @return the final score for the correct typed answers
     */
    private int calculateScoreFromEditText(String drStrange, String blackPanther) {

        int editScore = 0;

        //Both strings contain the correct names that can be entered for question 5
        String q5drStrange = "Dr Strange";
        String q5drStrange1 = "Doctor Strange";

        //If the user types in Dr Strange or Doctor Strange, score increases by two
        if (drStrange.toUpperCase().equals(q5drStrange.toUpperCase()) || drStrange.toUpperCase().equals(q5drStrange1.toUpperCase())) {
            editScore += 2;
        }

        //String contains the correct answer for question 6
        String q6blackpanther = "Black Panther";

        //If the user types in Black Panther, score increases by three
        if (blackPanther.toUpperCase().equals(q6blackpanther.toUpperCase())) {
            editScore += 3;
        }
        return editScore;
    }

    /**
     * This method returns the message to be displayed in the toast message if the user's score is less then 10
     *
     * @param choseIronMan     the user chose Iron Man
     * @param choseAvengers    the user chose the Avengers
     * @param choseThanos      the user chose Thanos
     * @param checkedNickFury  the user checked Nick Fury
     * @param checkedStanLee   the user checked  Stan Lee
     * @param checkedNatasha   the user checked  Natasha
     * @param checkedSamWilson the user checked  Sam Wilson
     * @param finalEditScore   the final score of two EditTextView (Question 5 and 6)
     * @return the text message for the toast message
     */

    private String getLessThenFullScoreMessage(boolean choseIronMan, boolean choseAvengers, boolean choseThanos, boolean checkedNickFury,
                                            boolean checkedStanLee, boolean checkedNatasha, boolean checkedSamWilson, int finalEditScore) {

        //scoreMessage will store the text for the totalscore integer and the text for each question that was incorrect
        String scoreMessage = "Your score is " + totalscore + "/10";
        scoreMessage += "\n";
        scoreMessage += "\n" + "Answer(s) that were incorrect: ";

        //Each If statement checks which questions were incorrect and stores the related text to scoreMessage
        if (!choseIronMan) {
            scoreMessage += "\n" + "Question 1";
        }

        if (!choseAvengers) {
            scoreMessage += "\n" + "Question 2";
        }

        if (!choseThanos) {
            scoreMessage += "\n" + "Question 3";
        }

        // The If statement checks the two selected answers in question 4's checkboxes
        // and determines if both answers are wrong
        if (checkedNatasha && checkedSamWilson) {
            scoreMessage += "\n" + "Question 4";
        }

        // The If statement checks the two selected answers in question 4's checkboxes
        // and determines which one of them was incorrect
        if (checkedNickFury && checkedNatasha) {
            scoreMessage += "\n" + "Question 4: one answer was incorrect";
        } else if (checkedNickFury && checkedSamWilson) {
            scoreMessage += "\n" + "Question 4: one answer was incorrect";
        } else if (checkedStanLee && checkedNatasha) {
            scoreMessage += "\n" + "Question 4: one answer was incorrect";
        } else if (checkedStanLee && checkedSamWilson) {
            scoreMessage += "\n" + "Question 4: one answer was incorrect";
        }

        //finalEditScore determines if the user entered both incorrect answers for question 5 and 6,
        //or if only one of them was incorrect (question 5 or 6)
        if (finalEditScore == 0) {
            scoreMessage += "\n" + "Question 5" + "\n" + "Question 6";
        } else if (finalEditScore == 2) {
            scoreMessage += "\n" + "Question 6";
        } else if (finalEditScore == 3) {
            scoreMessage += "\n" + "Question 5";
        }
        return scoreMessage;
    }

    /**
     * This method resets the quiz
     *
     * @param nickFuryCheckBox         is unchecked
     * @param stanLeeCheckBox          is unchecked
     * @param natashaCheckBox          is unchecked
     * @param samWilsonCheckBox        is unchecked
     * @param drStrangeEditTextView    is cleared
     * @param blackpantherEditTextView is cleared
     */
    private void resetQuestions(CheckBox nickFuryCheckBox, CheckBox stanLeeCheckBox, CheckBox natashaCheckBox
            , CheckBox samWilsonCheckBox, EditText drStrangeEditTextView, EditText blackpantherEditTextView) {

        //Reset score to zero
        totalscore = 0;

        //Clears all the radio groups
        RadioGroup q1RadioGroup = (RadioGroup) findViewById(R.id.q1_radio_group);
        q1RadioGroup.clearCheck();

        RadioGroup q2RadioGroup = (RadioGroup) findViewById(R.id.q2_radio_group);
        q2RadioGroup.clearCheck();

        RadioGroup q3RadioGroup = (RadioGroup) findViewById(R.id.q3_radio_group);
        q3RadioGroup.clearCheck();

        //Uncheck the checkboxes
        if (nickFuryCheckBox.isChecked()) {
            nickFuryCheckBox.setChecked(false);
        }

        if (stanLeeCheckBox.isChecked()) {
            stanLeeCheckBox.setChecked(false);
        }

        if (natashaCheckBox.isChecked()) {
            natashaCheckBox.setChecked(false);
        }

        if (samWilsonCheckBox.isChecked()) {
            samWilsonCheckBox.setChecked(false);
        }

        //Clears the text in the Edit Text of question 5 and returns the hint text
        drStrangeEditTextView.setText(null);
        //drStrangeEditTextView.clearFocus();

        //Clears the text in the Edit Text of question 6 and returns the hint text
        blackpantherEditTextView.setText(null);
        blackpantherEditTextView.clearFocus();

    }
}



