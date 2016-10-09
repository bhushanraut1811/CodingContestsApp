package apps.techmines.codingcontestapp.ui.component.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import apps.techmines.codingcontestapp.R;


/**
 * Activity as a dialog shows about app details
 */
public class AboutAppActivity extends Activity {
    private TextView mTvAboutApp;
    private TextView mTvReportBug;
    private TextView mTvOtherApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_app);
        mTvAboutApp = (TextView) findViewById(R.id.tv_about_app);
        mTvReportBug = (TextView) findViewById(R.id.tv_report_bug);
        mTvOtherApps = (TextView) findViewById(R.id.tv_other_apps);


        String html = "<h1><font color=\"black\">      Coding Contests  </font></h1>\n" +
                "\n" +
                "<p font color=\"black\">  A Place for all the running and upcoming coding contest from different platforms.</p>\n" +
                "\n" +
                "<p font color=\"black\"> Stay updated with all the latest contests.\n" +
                "Participate, Improve your Skills, Win amazing Prizes, get Hired.</p>\n" +
                "\n" +
                "<h2><font color=\"black\">   Credits:</h2>\n" +
                "\n" +
                "<div font color=\"black\">Icons made by <a href=\"http://www.flaticon.com/authors/freepik\" title=\"Freepik\">Freepik</a> from " +
                "<a href=\"http://www.flaticon.com\" title=\"Flaticon\">www.flaticon.com</a> is licensed by" +
                " <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>";


        mTvAboutApp.setText(Html.fromHtml(html));
        mTvAboutApp.setMovementMethod(LinkMovementMethod.getInstance());

        String reportBug = "<p>If you find any bug kindly report <u><font color=\"blue\">here</u>, So that we can improve your experience.</p>";

        String otherApps = "<div>Do check our other Apps @<a href=\"https://play.google.com/store/apps/developer?id=TechMines\">TechMines</a>";

        mTvReportBug.setText(Html.fromHtml(reportBug));
        mTvReportBug.setLinksClickable(true);
        mTvReportBug.setMovementMethod(LinkMovementMethod.getInstance());

        mTvOtherApps.setText(Html.fromHtml(otherApps));
        mTvOtherApps.setLinksClickable(true);
        mTvOtherApps.setMovementMethod(LinkMovementMethod.getInstance());

        mTvReportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse("techmines.apps@gmail.com"));
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Conding Contest Calender - Report Bug");
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"techmines.apps@gmail.com"});
                startActivity(sendIntent);
            }
        });
    }
}
