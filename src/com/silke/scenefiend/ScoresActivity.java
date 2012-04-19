package com.silke.scenefiend;

import com.silke.scenefiend.R;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoresActivity extends SceneFiendActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/lucindablack.ttf");
        TextView tv = (TextView) findViewById(R.id.CustomFont);
        tv.setTypeface(tf);
    
        // Set up the tabs
        TabHost host = (TabHost) findViewById(R.id.TabHost1);
        host.setup();
        
        // All Scores tab
        TabSpec allScoresTab = host.newTabSpec("allTab");
        allScoresTab.setIndicator(getResources().getString(R.string.all_scores), getResources().getDrawable(
                android.R.drawable.star_on));
        allScoresTab.setContent(R.id.ScrollViewAllScores);
        host.addTab(allScoresTab);
        
        // Friends Scores tab
        TabSpec myScoresTab = host.newTabSpec("myTab");
        myScoresTab.setIndicator(getResources().getString(R.string.my_scores), getResources().getDrawable(
                android.R.drawable.star_on));
        myScoresTab.setContent(R.id.ScrollViewMyScores);
        host.addTab(myScoresTab);
        
     // High scores tab
        TabSpec highScoresTab = host.newTabSpec("highscoresTab");
        highScoresTab.setIndicator(getResources().getString(R.string.high_scores), getResources().getDrawable(
                android.R.drawable.star_big_on));
        highScoresTab.setContent(R.id.ScrollViewHighScores);
        host.addTab(highScoresTab);
        
        // Set the default tab
        host.setCurrentTabByTag("allTab");
        
        // Retrieve the TableLayout references
        TableLayout allScoresTable = (TableLayout) findViewById(R.id.TableLayout_AllScores);
        TableLayout myScoresTable = (TableLayout) findViewById(R.id.TableLayout_MyScores);
        TableLayout highScoresTable = (TableLayout) findViewById(R.id.TableLayout_HighScores);
        
        // Give each TableLayout a yellow header row with the column names
        initializeHeaderRow(allScoresTable);
        initializeHeaderRow(myScoresTable);
        initializeHeaderRow(highScoresTable);
        XmlResourceParser mockAllScores = getResources().getXml(R.xml.allscores);
        XmlResourceParser mockMyScores = getResources().getXml(R.xml.myscores);
        XmlResourceParser mockHighScores = getResources().getXml(R.xml.highscores);
        try {
            processScores(allScoresTable, mockAllScores);
            processScores(myScoresTable, mockMyScores);
            processScores(highScoresTable, mockHighScores);
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Failed to load scores", e);
        }
    }
    /**
     * Add a header {@code TableRow} to the {@code TableLayout} (styled)
     * 
     * @param scoreTable
     *            the {@code TableLayout} that the header row will be added to
     */
    private void initializeHeaderRow(TableLayout scoreTable) {
        // Create the Table header row
        TableRow headerRow = new TableRow(this);
        int textColor = getResources().getColor(R.color.logo_color);
        float textSize = getResources().getDimension(R.dimen.score_heading_text_size);
        addTextToRowWithValues(headerRow, getResources().getString(R.string.username), textColor, textSize);
        addTextToRowWithValues(headerRow, getResources().getString(R.string.score), textColor, textSize);
        addTextToRowWithValues(headerRow, getResources().getString(R.string.rank), textColor, textSize);
        scoreTable.addView(headerRow);
    }

    /**
     * Churn through an XML score information and populate a {@code TableLayout}
     * 
     * @param scoreTable
     *            The {@code TableLayout} to populate
     * @param scores
     *            A standard {@code XmlResourceParser} containing the scores
     * @throws XmlPullParserException
     *             Thrown on XML errors
     * @throws IOException
     *             Thrown on IO errors reading the XML
     */
    private void processScores(final TableLayout scoreTable, XmlResourceParser scores) throws XmlPullParserException,
            IOException {
        int eventType = -1;
        boolean bFoundScores = false;
        // Find Score records from XML
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                // Get the name of the tag (eg scores or score)
                String strName = scores.getName();
                if (strName.equals("score")) {
                    bFoundScores = true;
                    String scoreValue = scores.getAttributeValue(null, "score");
                    String scoreRank = scores.getAttributeValue(null, "rank");
                    String scoreUserName = scores.getAttributeValue(null, "username");
                    insertScoreRow(scoreTable, scoreValue, scoreRank, scoreUserName);
                }
            }
            eventType = scores.next();
        }
        // Handle no scores available
        if (bFoundScores == false) {
            final TableRow newRow = new TableRow(this);
            TextView noResults = new TextView(this);
            noResults.setText(getResources().getString(R.string.no_scores));
            newRow.addView(noResults);
            scoreTable.addView(newRow);
        }
    }

    /**
     * {@code processScores()} helper method -- Inserts a new score {@code
     * TableRow} in the {@code TableLayout}
     * 
     * @param scoreTable
     *            The {@code TableLayout} to add the score to
     * @param scoreValue
     *            The value of the score
     * @param scoreRank
     *            The ranking of the score
     * @param scoreUserName
     *            The user who made the score
     */
    private void insertScoreRow(final TableLayout scoreTable, String scoreValue, String scoreRank, String scoreUserName) {
        final TableRow newRow = new TableRow(this);
        int textColor = getResources().getColor(R.color.title_color);
        float textSize = getResources().getDimension(R.dimen.help_text_size);
        addTextToRowWithValues(newRow, scoreUserName, textColor, textSize);
        addTextToRowWithValues(newRow, scoreValue, textColor, textSize);
        addTextToRowWithValues(newRow, scoreRank, textColor, textSize);
        scoreTable.addView(newRow);
    }

    /**
     * {@code insertScoreRow()} helper method -- Populate a {@code TableRow} with
     * three columns of {@code TextView} data (styled)
     * 
     * @param tableRow
     *            The {@code TableRow} the text is being added to
     * @param text
     *            The text to add
     * @param textColor
     *            The color to make the text
     * @param textSize
     *            The size to make the text
     */
    private void addTextToRowWithValues(final TableRow tableRow, String text, int textColor, float textSize) {
        TextView textView = new TextView(this);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setText(text);
        tableRow.addView(textView);
    }
}
