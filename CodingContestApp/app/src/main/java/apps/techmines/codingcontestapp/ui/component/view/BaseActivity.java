package apps.techmines.codingcontestapp.ui.component.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Holds common functionality for child activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * abstract method to initialise views in child activities
     */
    protected abstract void initViews();

    /**
     * shows message  on snack bar
     *
     * @param view
     * @param message
     */
    public void showSnackBar(final View view, final String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    protected void setUpHomeEnabled() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Shows loading progress dialog
     *
     * @param message
     */
    protected void showProgressDialog(final ProgressDialog progressDialog, String message) {
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    /**
     * Dismisses progress dialog
     *
     * @param progressDialog
     */
    protected void hideProgressDialog(final ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * shows toast message
     *
     * @param message
     */
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * checks network status true if Network state is up else false
     */
    protected boolean checkNetworkStatus() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();

        if ((activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

}
