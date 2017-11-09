package com.saurabh.fingregator.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.HashMap;

public class PlaidHelper {

    private static final String CLIENT_ID = "5a011f20bdc6a463729165d6";
    private static final String CLIENT_SECRET = "4929ca95be4a51bba6f7bf11648211";
    private static final String CLIENT_PUBLIC_KEY = "bd9303961f61492e30de5794add5da";

    private PlaidHelper() {
    }

    @NonNull
    public static HashMap<String, String> generateLinkOptions() {
        HashMap<String, String> linkInitializeOptions = new HashMap<>();
        linkInitializeOptions.put("key", CLIENT_PUBLIC_KEY);
        linkInitializeOptions.put("product", "auth");
        linkInitializeOptions.put("apiVersion", "v2");
        linkInitializeOptions.put("env", "sandbox");
        linkInitializeOptions.put("clientName", "Test App");
        linkInitializeOptions.put("selectAccount", "true");
        linkInitializeOptions.put("webhook", "http://requestb.in");
        linkInitializeOptions.put("baseUrl", "https://cdn.plaid.com/link/v2/stable/link.html");

        // If initializing Link in PATCH / update mode, also provide the public_token
        // linkInitializeOptions.put("public_token", "PUBLIC_TOKEN")

        return linkInitializeOptions;
    }

    @NonNull
    public static Uri generateLinkInitializationUrl(@NonNull HashMap<String, String> linkOptions) {
        Uri.Builder builder = Uri.parse(linkOptions.get("baseUrl"))
                .buildUpon()
                .appendQueryParameter("isWebview", "true")
                .appendQueryParameter("isMobile", "true");
        for (String key : linkOptions.keySet()) {
            if (!key.equals("baseUrl")) {
                builder.appendQueryParameter(key, linkOptions.get(key));
            }
        }
        return builder.build();
    }

    @NonNull
    public static HashMap<String, String> parseLinkUriData(@NonNull Uri linkUri) {
        HashMap<String, String> linkData = new HashMap<>();
        for (String key : linkUri.getQueryParameterNames()) {
            linkData.put(key, linkUri.getQueryParameter(key));
        }
        return linkData;
    }
}
