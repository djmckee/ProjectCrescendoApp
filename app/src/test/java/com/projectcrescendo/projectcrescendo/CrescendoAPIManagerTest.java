package com.projectcrescendo.projectcrescendo;

import junit.framework.TestCase;

/**
 * A JUnit unit test class to test the CrescendoAPIManager Composition Upload API client.
 *
 * Created by Dylan McKee on 09/04/16.
 */
public class CrescendoAPIManagerTest extends TestCase {

    private static final String SAMPLE_COMPOSITION = "hello_world";

    public void testUploadComposition() throws Exception {
        CrescendoAPIManager.uploadComposition(SAMPLE_COMPOSITION, new CrescendoAPIResponseHandler() {
            @Override
            public void uploadSucceeded(int uploadId, String uploadUrl) {
                // Test succeeded, validate response...
                if (uploadId < 0) {
                    // Not valid.
                    fail("Upload ID not valid.");
                }

                assertNotNull("No URL returned from API", uploadUrl);
            }

            @Override
            public void uploadFailed() {
                // Test failed
                fail("Upload failed");
            }
        });
    }
}