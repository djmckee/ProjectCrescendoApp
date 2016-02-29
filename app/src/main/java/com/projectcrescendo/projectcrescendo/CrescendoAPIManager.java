package com.projectcrescendo.projectcrescendo;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class makes use of the LoopJ library, downloaded from http://loopj.com/android-async-http/
 * via Gradle on 29/02/16, and originally authored by James Smith.
 *
 */
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

/**
 * An interface to handle callbacks from the API on upload success/failure.
 */
interface CrescendoAPIResponseHandler {
    void uploadSucceeded(int uploadId, String uploadUrl);
    void uploadFailed();

}


/**
 * This class manages interaction with the Crescendo web API to allow MusicXML composition strings
 * to be uploaded to our cloud platform.
 *
 * This class makes extensive use of the LoopJ HTTP library to handle HTTP requests asynchronously,
 * LoopJ was originally authored by James Smith, and downloaded by me through Gradle on 29/02/16.
 *
 * I also looked at the example LoopJ code at http://loopj.com/android-async-http/ when writing
 * this class.
 *
 * Created by Dylan McKee on 29/02/16.
 */
public class CrescendoAPIManager {
    // I took the following class structure from http://loopj.com/android-async-http/

    private static final String API_BASE_URL = "http://homepages.cs.ncl.ac.uk/2015-16/csc2022_team13/";

    private static AsyncHttpClient httpClient = new AsyncHttpClient();

    // I took the following method from http://loopj.com/android-async-http/
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        httpClient.post(getAbsoluteUrl(url), params, responseHandler);

    }

    // I took the following method from http://loopj.com/android-async-http/
    private static String getAbsoluteUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public static void uploadComposition(String compositionXml, final CrescendoAPIResponseHandler responseHandler) {
        final String UPLOAD_ENDPOINT = "upload.php";

        RequestParams params = new RequestParams();
        params.put("musicxml_content", compositionXml);

        Log.d("CrescendoAPI params", params.toString());

        post(UPLOAD_ENDPOINT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Success, lets parse the JSON...
                // Create some placeholders
                int uploadId = -1;
                String uploadUrl = null;

                // Parse out the ID of the upload from the API object's 'composition_id' key
                try {
                    uploadId = response.getInt("composition_id");
                } catch (JSONException jayZed) {
                    // Something went wrong
                    Log.d("CrescendoAPI", "Parse failed, could not parse upload ID out of JSON response");

                }

                // Now lets parse the URL of the composition from the 'composition_view_url' key of the object
                try {
                    uploadUrl = response.getString("composition_view_url");

                } catch (JSONException jayZ) {
                    // Something went wrong
                    Log.d("CrescendoAPI", "Parse failed, could not parse upload URL out of JSON response");

                }

                // Only call success callback if we parsed correctly...
                if (uploadId > 0 && uploadUrl != null) {
                    // Call success callback passing in what we've parsed out
                    responseHandler.uploadSucceeded(uploadId, uploadUrl);

                } else {
                    // Parsing failed, something went wrong
                    responseHandler.uploadFailed();

                }


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                // If the response is JSONArray instead of expected JSONObject
                // Something went wrong....
                Log.d("CrescendoAPI", "Response code: " + statusCode);
                Log.d("CrescendoAPI", "Request failed: " + array);

                responseHandler.uploadFailed();

            }

            @Override
            public void onFailure(int statusCode,
                                  cz.msebera.android.httpclient.Header[] headers,
                                  java.lang.String responseString,
                                  java.lang.Throwable throwable) {
                Log.d("CrescendoAPI", "Response code: " + statusCode);
                Log.d("CrescendoAPI", "Request failed: " + responseString);

                // Something went wrong....
                responseHandler.uploadFailed();

            }

            @Override
            public void onFailure(int statusCode,
                                  cz.msebera.android.httpclient.Header[] headers,
                                  java.lang.Throwable throwable,
                                  org.json.JSONObject errorResponse) {
                Log.d("CrescendoAPI", "Response code: " + statusCode);
                Log.d("CrescendoAPI", "Request failed: " + errorResponse);

                responseHandler.uploadFailed();

            }

            public void onFailure(int statusCode,
                                  cz.msebera.android.httpclient.Header[] headers,
                                  java.lang.Throwable throwable,
                                  org.json.JSONArray errorResponse) {
                Log.d("CrescendoAPI", "Response code: " + statusCode);
                Log.d("CrescendoAPI", "Request failed: " + errorResponse);

                responseHandler.uploadFailed();

            }

        });

    }

}
