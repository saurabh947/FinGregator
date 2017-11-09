package com.saurabh.fingregator.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.saurabh.fingregator.R;
import com.saurabh.fingregator.utils.PlaidHelper;

import java.util.HashMap;

public class LinkAccountsActivity extends BaseActivity {
    public static final String TAG = LinkAccountsActivity.class.getCanonicalName();

    private WebView mLinkWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_accounts);
        initComponents();
        syncUi();
    }

    private void initComponents() {
        mLinkWebView = (WebView)findViewById(R.id.webview_link_accounts);
        WebSettings webSettings = mLinkWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private void syncUi() {
        HashMap<String, String> linkOptions = PlaidHelper.generateLinkOptions();
        final Uri linkInitializationUrl = PlaidHelper.generateLinkInitializationUrl(linkOptions);

        mLinkWebView.loadUrl(linkInitializationUrl.toString());

        mLinkWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // Parse the URL to determine if it's a special Plaid Link redirect or a request
                // for a standard URL (typically a forgotten password or account not setup link).
                // Handle Plaid Link redirects and open traditional pages directly in the  user's
                // preferred browser.
                Uri parsedUri = Uri.parse(url);
                if (parsedUri.getScheme().equalsIgnoreCase("plaidlink")) {

                    String action = parsedUri.getHost();
                    HashMap<String, String> linkData = PlaidHelper.parseLinkUriData(parsedUri);

                    if (action.equals("connected")) {
                        // TODO: Move ahead
                        // User successfully linked
                        Log.d("Public token: ", linkData.get("public_token"));
                        Log.d("Account ID: ", linkData.get("account_id"));
                        Log.d("Account name: ", linkData.get("account_name"));
                        Log.d("Institution type: ", linkData.get("institution_type"));
                        Log.d("Institution name: ", linkData.get("institution_name"));

                        // Reload Link in the Webview
                        // You will likely want to transition the view at this point.
                        mLinkWebView.loadUrl(linkInitializationUrl.toString());
                    } else if (action.equals("exit")) {
                        // TODO: User exited
                        // linkData may contain information about the user's status in the Link flow,
                        // the institution selected, information about any error encountered,
                        // and relevant API request IDs.
                        Log.d("User status in flow: ", linkData.get("status"));
                        // The requet ID keys may or may not exist depending on when the user exited
                        // the Link flow.
                        Log.d("Link request ID: ", linkData.get("link_request_id"));
                        Log.d("API request ID: ", linkData.get("plaid_api_request_id"));

                        // Reload Link in the Webview
                        // You will likely want to transition the view at this point.
                        mLinkWebView.loadUrl(linkInitializationUrl.toString());
                    } else {
                        Log.d("Link action detected: ", action);
                    }
                    // Override URL loading
                    return true;
                } else if (parsedUri.getScheme().equalsIgnoreCase("https") ||
                        parsedUri.getScheme().equalsIgnoreCase("http")) {
                    // Open in browser - this is most  typically for 'account locked' or
                    // 'forgotten password' redirects
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    // Override URL loading
                    return true;
                } else {
                    // Unknown case - do not override URL loading
                    Log.i(TAG, "Unknown URI scheme -- " + parsedUri.getScheme());
                    return false;
                }
            }
        });
    }
}
