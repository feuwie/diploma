/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.verify.v2.service;

import com.twilio.base.Creator;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

public class RateLimitCreator extends Creator<RateLimit> {
    private final String pathServiceSid;
    private final String uniqueName;
    private String description;

    /**
     * Construct a new RateLimitCreator.
     *
     * @param pathServiceSid The SID of the Service that the resource is associated
     *                       with
     * @param uniqueName A unique, developer assigned name of this Rate Limit.
     */
    public RateLimitCreator(final String pathServiceSid,
                            final String uniqueName) {
        this.pathServiceSid = pathServiceSid;
        this.uniqueName = uniqueName;
    }

    /**
     * Description of this Rate Limit.
     *
     * @param description Description of this Rate Limit
     * @return this
     */
    public RateLimitCreator setDescription(final String description) {
        this.description = description;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the create.
     *
     * @param client TwilioRestClient with which to make the request
     * @return Created RateLimit
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public RateLimit create(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            Domains.VERIFY.toString(),
            "/v2/Services/" + this.pathServiceSid + "/RateLimits",
            client.getRegion()
        );

        addPostParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("RateLimit creation failed: Unable to connect to server");
        } else if (!TwilioRestClient.SUCCESS.apply(response.getStatusCode())) {
            RestException restException = RestException.fromJson(response.getStream(), client.getObjectMapper());
            if (restException == null) {
                throw new ApiException("Server Error, no content");
            }

            throw new ApiException(
                restException.getMessage(),
                restException.getCode(),
                restException.getMoreInfo(),
                restException.getStatus(),
                null
            );
        }

        return RateLimit.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (uniqueName != null) {
            request.addPostParam("UniqueName", uniqueName);
        }

        if (description != null) {
            request.addPostParam("Description", description);
        }
    }
}