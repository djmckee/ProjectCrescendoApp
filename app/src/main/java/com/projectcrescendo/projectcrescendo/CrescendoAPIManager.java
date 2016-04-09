package com.projectcrescendo.projectcrescendo;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * This class makes use of the LoopJ library, downloaded from http://loopj.com/android-async-http/
 * via Gradle on 29/02/16, and originally authored by James Smith.
 */

/**
 * An interface to handle callbacks from the API on upload success/failure.
 * <p>
 * Created by Dylan McKee on 29/02/16.
 */
interface CrescendoAPIResponseHandler {
    /**
     * This method is called upon upload success, this means that the composition is valid and has
     * successfully been uploaded to the Crescendo API.
     * <p>
     * This callback hands back an id for the upload, as an integer, and a URL containing a
     * download link for the upload, as a String.
     *
     * @param uploadId  an integer containing the ID of that upload.
     * @param uploadUrl a String containing the URL to that upload, so the user can download their
     *                  composition.
     */
    void uploadSucceeded(int uploadId, String uploadUrl);

    /**
     * This callback method is fired when an upload to the Crescendo API has failed.
     */
    void uploadFailed();

}


/**
 * This class manages interaction with the Crescendo web API to allow MusicXML composition strings
 * to be uploaded to our cloud platform.
 * <p>
 * This class makes extensive use of the LoopJ HTTP library to handle HTTP requests asynchronously,
 * LoopJ was originally authored by James Smith, and downloaded by me through Gradle on 29/02/16.
 * <p>
 * I also looked at the example LoopJ code at http://loopj.com/android-async-http/ when writing
 * this class.
 * <p>
 * Created by Dylan McKee on 29/02/16.
 */
public class CrescendoAPIManager {
    // I took the following class structure from http://loopj.com/android-async-http/

    /**
     * The base URL for our API endpoints, as a String.
     */
    private static final String API_BASE_URL = "http://homepages.cs.ncl.ac.uk/2015-16/csc2022_team13/";

    /**
     * The LoopJ Async HTTP Client instance, to make our HTTP requests to the API.
     */
    private static AsyncHttpClient httpClient = new AsyncHttpClient();

    /**
     * The Client ID for our app. This MUST match the Client ID for the app on the server-side.
     */
    private static String API_CLIENT_ID = "VJyR29hQKnwG3R8CjbN6";

    /**
     * The Client Secret for our app. This MUST match the Client Secret for the app on the server-side.
     */
    private static String API_CLIENT_SECRET = "HgNzSKYJqQKyNT2mGVSUr4cUvMcGs2Ym7x3U5dHA";

    /**
     * A generic POST method to send a POST HTTP request.
     *
     * @param url             the URL to send the request to.
     * @param params          the parameters to include in the request.
     * @param responseHandler a callback to handle the response from the request.
     */
    // I took the following method from http://loopj.com/android-async-http/
    private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        httpClient.post(getAbsoluteUrl(url), params, responseHandler);

    }

    /**
     * A method that returns the endpoint passed to it appended on to the API's base URL.
     *
     * @param relativeUrl the API endpoint.
     * @return the endpoint appended to the API's base URL.
     */
    // I took the following method from http://loopj.com/android-async-http/
    private static String getAbsoluteUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    /**
     * An Async upload method to asynchronously upload a MusicXML encoded composition to the
     * Crescendo API, and then callback the handler passed to it upon successful completion
     * (or upload failure).
     *
     * @param compositionXml  the MusicXML representation of the composition to upload, as a String,
     * @param responseHandler the callback class, must conform to the CrescendoAPIResponseHandler interface.
     */
    public static void uploadComposition(String compositionXml, final CrescendoAPIResponseHandler responseHandler) {
        final String UPLOAD_ENDPOINT = "upload.php";

        RequestParams params = new RequestParams();
        params.put("musicxml_content", compositionXml);
        params.put("client_id", API_CLIENT_ID);
        params.put("client_secret", API_CLIENT_SECRET);

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
                    if (responseHandler != null) {
                        responseHandler.uploadSucceeded(uploadId, uploadUrl);

                    }

                } else {
                    // Parsing failed, something went wrong
                    if (responseHandler != null) {
                        responseHandler.uploadFailed();
                    }

                }


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                // If the response is JSONArray instead of expected JSONObject
                // Something went wrong....
                Log.d("CrescendoAPI", "Response code: " + statusCode);
                Log.d("CrescendoAPI", "Request failed: " + array);

                if (responseHandler != null) {
                    responseHandler.uploadFailed();
                }

            }

            @Override
            public void onFailure(int statusCode,
                                  cz.msebera.android.httpclient.Header[] headers,
                                  java.lang.String responseString,
                                  java.lang.Throwable throwable) {
                Log.d("CrescendoAPI", "Response code: " + statusCode);
                Log.d("CrescendoAPI", "Request failed: " + responseString);

                // Something went wrong...
                if (responseHandler != null) {
                    responseHandler.uploadFailed();
                }

            }

            @Override
            public void onFailure(int statusCode,
                                  cz.msebera.android.httpclient.Header[] headers,
                                  java.lang.Throwable throwable,
                                  org.json.JSONObject errorResponse) {
                Log.d("CrescendoAPI", "Response code: " + statusCode);
                Log.d("CrescendoAPI", "Request failed: " + errorResponse);

                if (responseHandler != null) {
                    responseHandler.uploadFailed();
                }

            }

            public void onFailure(int statusCode,
                                  cz.msebera.android.httpclient.Header[] headers,
                                  java.lang.Throwable throwable,
                                  org.json.JSONArray errorResponse) {
                Log.d("CrescendoAPI", "Response code: " + statusCode);
                Log.d("CrescendoAPI", "Request failed: " + errorResponse);

                if (responseHandler != null) {
                    responseHandler.uploadFailed();
                }

            }

        });

    }

}
