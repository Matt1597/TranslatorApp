package com.example.translator1;

import android.support.v7.app.ActionBarActivity;

import java.util.Locale;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

@SuppressWarnings("deprecation")
@SuppressLint({ "NewApi", "DefaultLocale" })

public class MainActivity extends ActionBarActivity implements OnInitListener {

	// fields.
	private TextToSpeech textToSpeech;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textToSpeech = new TextToSpeech(this, this);
		//Set Default language to English.
		((Spinner)findViewById(R.id.language_choice)).setSelection(8);
		// When translate button is pressed this executes.
		((Button) findViewById(R.id.translate)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				class translate extends AsyncTask<Void, Void, Void> {

					String translatedText = "";

					@Override
					protected Void doInBackground(Void... params) {
						try {
							String text = ((EditText) findViewById(R.id.txt_input)).getText().toString();
							translatedText = translate(text);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							translatedText = e.toString();
						}

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						((TextView) findViewById(R.id.display)).setText(translatedText);
						super.onPostExecute(result);
					}

				}
				new translate().execute();
			}

		});

		// When the Speak button is pressed this code is executed.
		((Button) findViewById(R.id.speak)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Takes the text in the display TextView and uses it with
				// Google TextToSpeak.
				speak(((TextView) findViewById(R.id.display)).getText().toString());
			}

		});

		// When item on the language selector is chosen this code executes.
		((Spinner) findViewById(R.id.language_choice)).setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Spinner language_choice = (Spinner) findViewById(R.id.language_choice);

				String language = language_choice.getSelectedItem().toString().toLowerCase();

				ImageView imageView = (ImageView) findViewById(R.id.flag);
				String flag = language;
				String PACKAGE_NAME = getApplicationContext().getPackageName();
				int imgId = getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + flag, null, null);

				imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));

				class translate extends AsyncTask<Void, Void, Void> {

					String translatedText = "";

					@Override
					protected Void doInBackground(Void... params) {
						try {
							String text = ((EditText) findViewById(R.id.txt_input)).getText().toString();
							translatedText = translate(text);
						} catch (Exception e) {
							// TODO Auto-generated catch block.
							e.printStackTrace();
							translatedText = e.toString();
						}

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub.
						((TextView) findViewById(R.id.display)).setText(translatedText);
						super.onPostExecute(result);
					}

				}
				new translate().execute();

				// checks whether chosen language is supported with TextToSpeech
				// or not.
				((TextView) findViewById(R.id.message)).setText("");
				Button speak = (Button) findViewById(R.id.speak);
				speak.setEnabled(true);

				if (language.equals("chinese_simplified")) {
					// set language of TextToSpeech to Chinese.
					textToSpeech.setLanguage(Locale.SIMPLIFIED_CHINESE);

				} else if (language.equals("chinese_traditional")) {
					// set language of TextToSpeech to Chinese.
					textToSpeech.setLanguage(Locale.TRADITIONAL_CHINESE);

				} else if (language.equals("english")) {
					// set language of TextToSpeech to English.
					textToSpeech.setLanguage(Locale.ENGLISH);

				} else if (language.equals("french")) {
					// set language of TextToSpeech to French.
					textToSpeech.setLanguage(Locale.FRENCH);

				} else if (language.equals("german")) {
					// set language of TextToSpeech to German.
					textToSpeech.setLanguage(Locale.GERMAN);

				} else if (language.equals("italian")) {
					// set language of TextToSpeech to Italian.
					textToSpeech.setLanguage(Locale.ITALIAN);

				} else if (language.equals("japanese")) {
					// set language of TextToSpeech to Japanese.
					textToSpeech.setLanguage(Locale.JAPANESE);

				} else if (language.equals("korean")) {
					// set language of TextToSpeech to Korean.
					textToSpeech.setLanguage(Locale.KOREAN);

				} else if (language.equals("catalan") || language.equals("czech") || language.equals("danish")
						|| language.equals("dutch") || language.equals("estonian") || language.equals("finnish")
						|| language.equals("haitian_creole") || language.equals("hungarian")
						|| language.equals("indonesian") || language.equals("latvian") || language.equals("lithuanian")
						|| language.equals("malay") || language.equals("norwegian") || language.equals("polish")
						|| language.equals("portuguese") || language.equals("romanian") || language.equals("slovak")
						|| language.equals("slovenian") || language.equals("spanish") || language.equals("swedish")
						|| language.equals("turkish") || language.equals("vietnamese") || language.equals("greek")) {
					// tell user that language does not have accent.
					textToSpeech.setLanguage(Locale.GERMAN);
					((TextView) findViewById(R.id.message)).setText("Accent not supported for " + language + ".");
				} else {
					// disable the speak button for the languages that are not
					// supported.
					speak.setEnabled(false);
					((TextView) findViewById(R.id.message)).setText("TextToSpeech unavailable for " + language + ".");
				}

			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});
	}

	public String translate(String text) throws Exception {
		// Translate text from any language to any of the languages listed in
		// the Spinner.
		Translate.setClientId("Matthew_Translator");
		Translate.setClientSecret("SwiFDMGVCwL/WK06ebu836RlM/GnLflG5YXXSOdZGWw=");

		String translatedText = "";
		String language = ((Spinner) findViewById(R.id.language_choice)).getSelectedItem().toString().toUpperCase();

		try {
			translatedText = Translate.execute(text, Language.AUTO_DETECT, Language.valueOf(language));
		} catch (Exception e) {
			e.printStackTrace();
			translatedText = e.toString();
			((TextView) findViewById(R.id.display)).setText("Error Occured, language not recognised");

		}

		return translatedText;
	}

	private void speak(String text) {
//Speaks the translated text using TextToSpeech.
		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

	}

	@Override
	public void onInit(int status) {

	}
}
