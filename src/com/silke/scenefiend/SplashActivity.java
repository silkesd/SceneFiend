package com.silke.scenefiend;

import com.silke.scenefiend.R;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class SplashActivity extends SceneFiendActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/lucindablack.ttf");
        TextView tv = (TextView) findViewById(R.id.CustomFont);
        tv.setTypeface(tf);
        
        //calling the animation
        startAnimating();
    }
    
    //starting the animation on the splash screen 
    private void startAnimating() 
    {
    	
    	// Fade in the top title
        TextView logo1 = (TextView) findViewById(R.id.CustomFont);
        Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in_title);
        logo1.startAnimation(fade1);
    
        // Fade in the description of the game after a built-in delay in the anim folder.
        TextView logo2 = (TextView) findViewById(R.id.TextViewTopTitleDesc);
        Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in_title_desc);
        logo2.startAnimation(fade2);
        
        // Transition to Main Menu - WILL CHANGE TO LOGIN LATER when description finishes animating
        fade2.setAnimationListener(new AnimationListener() 
        {
            public void onAnimationEnd(Animation animation) 
            {
            	// The animation has ended, transition to the Login screen
            	startActivity(new Intent(SplashActivity.this, MenuActivity.class));
                SplashActivity.this.finish();
            }

            public void onAnimationRepeat(Animation animation) 
            {
            }

            public void onAnimationStart(Animation animation) 
            {
            }
        });
       
    }
  
    @Override
    //if the user pauses the splqsh animation
    protected void onPause() 
    {
        super.onPause();
        // Stop the animations
        TextView logo1 = (TextView) findViewById(R.id.CustomFont);
        logo1.clearAnimation();
        
        TextView logo2 = (TextView) findViewById(R.id.TextViewTopTitleDesc);
        logo2.clearAnimation();

    }
    
    @Override
    //if the user returns to the animation after pausing
    protected void onResume() 
    {
        super.onResume();

        // Start again from the beginning of the splash animation
        startAnimating();
    }
}
