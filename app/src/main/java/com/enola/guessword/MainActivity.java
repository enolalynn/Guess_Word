package com.enola.guessword;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore, tvGuessWord, tvMatchPlayed;
    private EditText etYourGuess;
    private Button btGuess;
    private  int score, matchPlayed;
    private String guessWord;
   // private final String [] words = {"apple", "banana", "grape", "strawberry" , "lychee", "orange", "chocolate", "coffee", "lemon","passion"};
   private final String [] words = {"apple", "banana", "grape", "strawberry" , "lychee", "orange", "chocolate", "coffee", "lemon","passion"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        initListener();

    }

    private  void  initListener(){

        btGuess.setOnClickListener(v -> {
            if (matchPlayed == words.length){
                if(score > 5){
                    tvGuessWord.setText("WINNER!");
                }else {
                    tvGuessWord.setText("TRY AGAIN!");
                }
                etYourGuess.setText("");
                Log.d("GameInfo", "Correct Guess");
                return;
            }
            matchPlayed++;
            tvMatchPlayed.setText("Match Played : " + matchPlayed);
            String myGuess = etYourGuess.getText().toString();
            Log.d("GameInfo", "My Guess: " + myGuess);
            Log.d("GameInfo", "Guess Word: " + guessWord);
            if (isGuessCorrect(myGuess,guessWord) ){
                //correct

                score++;
                tvScore.setText(score + "/10");
                Log.d("GameInfo", "Correct Guess");
            }else {
                Log.d("GameInfo","Incorrect Guess");
            }
            nextRound();
        });
    }

    private boolean isGuessCorrect(String word1, String word2) {
        char[] guessChars = word1.toCharArray();
        char[] shuffleChars = word2.toCharArray();

        List<Character> shuffleList = new ArrayList<>();
        for(char c : shuffleChars){
            shuffleList.add(c);
        }
        for (char c : guessChars){
            if (shuffleList.contains(c)) {
                shuffleList.remove(Character.valueOf(c));
            }else{
                return false;
            }
        }
        return true;
    }

    private void  nextRound(){
        String guessWord = getRandomWordFromWords();
        tvGuessWord.setText(guessWord);
        etYourGuess.setText("");
        Log.d("GameInfo", "Next Round: " + guessWord);
    }

    private void initUi(){
        tvScore = findViewById(R.id.tvScore);
        tvGuessWord = findViewById(R.id.tvGuessWord);
        tvMatchPlayed = findViewById(R.id.tvMatchPlayed);
        etYourGuess = findViewById(R.id.etYourGuess);
        btGuess = findViewById(R.id.btGuess);
        tvGuessWord.setText(getRandomWordFromWords());
    }

    private String getRandomWordFromWords(){
       ArrayList<String> myWords = new ArrayList<String>(Arrays.asList(words));
       Random random = new Random();
            Collections.shuffle(myWords,random);
            String randomWord = myWords.get(0);
            guessWord = shuffleWord(randomWord);
            return guessWord;
    }

    private String shuffleWord(String randomWord) {
        List<String> letters = Arrays.asList(randomWord.split(""));
        Collections.shuffle(letters);
        StringBuilder shuffleWord = new StringBuilder();
        for(String letter : letters){
            shuffleWord.append(letter);
        }
        return  shuffleWord.toString();
    }
}