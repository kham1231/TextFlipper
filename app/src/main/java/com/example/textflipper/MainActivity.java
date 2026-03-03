package com.example.textflipper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.HashMap;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        boolean isReadOnly = getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);

        if (text != null && !isReadOnly) {
            String modifiedText = replaceCyrillicWithLatin(text.toString());
            Intent resultIntent = new Intent();
            resultIntent.putExtra(Intent.EXTRA_PROCESS_TEXT, modifiedText);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    private String replaceCyrillicWithLatin(String input) {
        HashMap<Character, Character> map = new HashMap<>();
        map.put('а', 'a'); map.put('А', 'A'); map.put('В', 'B'); 
        map.put('Е', 'E'); map.put('е', 'e'); map.put('К', 'K'); 
        map.put('М', 'M'); map.put('Н', 'H'); map.put('О', 'O'); 
        map.put('о', 'o'); map.put('Р', 'P'); map.put('р', 'p'); 
        map.put('С', 'C'); map.put('с', 'c'); map.put('Т', 'T'); 
        map.put('у', 'y'); map.put('Х', 'X'); map.put('х', 'x');

        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append(map.containsKey(c) ? map.get(c) : c);
        }
        return result.toString();
  
    }
}
