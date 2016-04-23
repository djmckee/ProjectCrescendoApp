package com.projectcrescendo.projectcrescendo;

import junit.framework.TestCase;

/**
 * A JUnit unit test class to test the CrescendoAPIManager Composition Upload API client.
 *
 * Created by Dylan McKee on 09/04/16.
 */
public class CrescendoAPIManagerTest extends TestCase {

    /**
     * A sample string to upload to the API for testing purposes.
     */
    private static final String SAMPLE_COMPOSITION = "hello_world";

    /**
     * This test tests the upload method of teh Crescendo API Manager class. It connects to the
     * server-side API, uploads the sample string constant defined above, and then ensures that a
     * valid Upload ID and Upload URL have been returned from the API and correctly parsed by the
     * API manager; as per the API documentation.
     *
     * @throws Exception unknown exception thrown only if the test fails with a fatal error.
     */
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