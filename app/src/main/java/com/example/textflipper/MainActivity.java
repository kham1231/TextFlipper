package com.example.textflipper;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.util.HashMap;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Получаем выделенный текст
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        boolean isReadOnly = getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);

        if (text != null) {
            // Переворачиваем буквы
            String modifiedText = replaceCyrillicWithLatin(text.toString());

            // --- НОВАЯ ФУНКЦИЯ: КОПИРОВАНИЕ В БУФЕР ОБМЕНА ---
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Перевертыш", modifiedText);
            clipboard.setPrimaryClip(clip);

            // Показываем маленькое системное уведомление, что текст скопирован
            Toast.makeText(this, "Перевернуто и скопировано!", Toast.LENGTH_SHORT).show();

            // Если поле можно редактировать (например, мы пишем сообщение, а не читаем сайт), 
            // то возвращаем измененный текст обратно на экран
            if (!isReadOnly) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Intent.EXTRA_PROCESS_TEXT, modifiedText);
                setResult(RESULT_OK, resultIntent);
            }
        }
        
        // Закрываем программу
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

        // Указываем input.length(), чтобы программа выделяла ровно столько памяти, сколько нужно для огромных текстов
        StringBuilder result = new StringBuilder(input.length());
        for (char c : input.toCharArray()) {
            result.append(map.containsKey(c) ? map.get(c) : c);
        }
        return result.toString();
    }
}
