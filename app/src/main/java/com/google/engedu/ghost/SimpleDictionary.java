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

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        String currentWord = null;

        /*int mid = 0;
        int lower = 0;
        int upper = words.size();
        while(lower <= upper){
            Log.i("LOG", "l = " + lower + "  u= " + upper +  " mid = " + mid + " length : " + words.get(mid).length());
            mid = (lower+upper-1)/2;
            Log.i("LOG", "l = " + lower + "  u= " + upper +  " mid = " + mid + " length : " + words.get(mid).length());

            if(words.get(mid).length()>prefix.length() && prefix.equals(words.get(mid).substring(0,(prefix.length()-1)))){
                currentWord = words.get(mid);
                break;
            }
            else if(words.get(mid).length()>prefix.length() && prefix.compareToIgnoreCase(words.get(mid).substring(0,prefix.length()-1))<0){
                upper = mid-1;
            }
            else if(words.get(mid).length()>prefix.length()){
                lower = mid+1;
            }
        }*/

        for (String i: words){
            if(i.startsWith(prefix)) {
                currentWord = i;
            }

        }

        return currentWord;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }
}
