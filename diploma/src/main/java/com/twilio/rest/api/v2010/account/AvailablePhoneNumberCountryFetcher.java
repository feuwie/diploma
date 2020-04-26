/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.api.v2010.account;

import com.twilio.base.Fetcher;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

public class AvailablePhoneNumberCountryFetcher extends Fetcher<AvailablePhoneNumberCountry> {
    private String pathAccountSid;
    private final String pathCountryCode;

    /**
     * Construct a new AvailablePhoneNumberCountryFetcher.
     *
     * @param pathCountryCode The ISO country code of the country to fetch
     *                        available phone number information about
     */
    public AvailablePhoneNumberCountryFetcher(final String pathCountryCode) {
        this.pathCountryCode = pathCountryCode;
    }

    /**
     * Construct a new AvailablePhoneNumberCountryFetcher.
     *
     * @param pathAccountSid The SID of the Account requesting the available phone
     *                       number Country resource
     * @param pathCountryCode The ISO country code of the country to fetch
     *                        available phone number information about
     */
    public AvailablePhoneNumberCountryFetcher(final String pathAccountSid,
                                              final String pathCountryCode) {
        this.pathAccountSid = pathAccountSid;
        this.pathCountryCode = pathCountryCode;
    }

    /**
     * Make the request to the Twilio API to perform the fetch.
     *
     * @param client TwilioRestClient with which to make the request
     * @return Fetched AvailablePhoneNumberCountry
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public AvailablePhoneNumberCountry fetch(final TwilioRestClient client) {
        this.pathAccountSid = this.pathAccountSid == null ? client.getAccountSid() : this.pathAccountSid;
        Request request = new Request(
            HttpMethod.GET,
            Domains.API.toString(),
            "/2010-04-01/Accounts/" + this.pathAccountSid + "/AvailablePhoneNumbers/" + this.pathCountryCode + ".json",
            client.getRegion()
        );

        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("AvailablePhoneNumberCountry fetch failed: Unable to connect to server");
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

        return AvailablePhoneNumberCountry.fromJson(response.getStream(), client.getObjectMapper());
    }
}