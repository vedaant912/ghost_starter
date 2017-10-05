/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();

        try {
            dictionary = new SimpleDictionary(assetManager.open("words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        Button challenge = (Button)findViewById(R.id.challenge_btn);
        challenge.setEnabled(true);
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView status = (TextView) findViewById(R.id.gameStatus);
        TextView ghostText = (TextView) findViewById(R.id.ghostText);
        Button challenge = (Button)findViewById(R.id.challenge_btn);
        String CurrentWord = (String)ghostText.getText();
        String word = null;
        // Do computer turn stuff then make it the user's turn again

        if(CurrentWord.length()>=4){
            if(dictionary.isWord(CurrentWord)){
                status.setText("Computer Wins!");
                challenge.setEnabled(false);
                return;
            }
        }

        word = dictionary.getAnyWordStartingWith(CurrentWord);
        if(word==null){
            //challenge user
        }
        else{
            CurrentWord = word.substring(0,CurrentWord.length()+1);
            ghostText.setText(CurrentWord);
        }
        //
        userTurn = true;
        status.setText(USER_TURN);
    }

    public void onChallenge(View view){
        TextView ghostText = (TextView) findViewById(R.id.ghostText);
        TextView status = (TextView) findViewById(R.id.gameStatus);
        Button challenge = (Button)findViewById(R.id.challenge_btn);
        String word = (String)ghostText.getText();
        if(word.length()>=4){
            if(dictionary.isWord(word)){
                status.setText("You Win!");
                challenge.setEnabled(false);
                return;
            }
            else{
                String temp = dictionary.getAnyWordStartingWith(word);
                if(temp!=null){
                    ghostText.setText(temp);
                    status.setText("Computer Wins!!");
                    challenge.setEnabled(false);
                    return;
                }
                else {
                    status.setText("You Win!");
                    challenge.setEnabled(false);
                    return;
                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Not yet!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        TextView ghostText = (TextView) findViewById(R.id.ghostText);
        TextView status = (TextView) findViewById(R.id.gameStatus);

        if(keyCode >= 29 && keyCode <= 54)
        {
            String temp = (String) ghostText.getText();
            temp += (char)event.getUnicodeChar();
            ghostText.setText(temp);
            status.setText(COMPUTER_TURN);
            computerTurn();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Alphabets Only!", Toast.LENGTH_SHORT).show();
        }



        return super.onKeyUp(keyCode, event);
    }
}
