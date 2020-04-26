/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.ipmessaging.v1.service.channel;

import com.twilio.base.Updater;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

public class MemberUpdater extends Updater<Member> {
    private final String pathServiceSid;
    private final String pathChannelSid;
    private final String pathSid;
    private String roleSid;
    private Integer lastConsumedMessageIndex;

    /**
     * Construct a new MemberUpdater.
     *
     * @param pathServiceSid The SID of the Service to create the resource under
     * @param pathChannelSid The unique ID of the channel the member to update
     *                       belongs to
     * @param pathSid The unique string that identifies the resource
     */
    public MemberUpdater(final String pathServiceSid,
                         final String pathChannelSid,
                         final String pathSid) {
        this.pathServiceSid = pathServiceSid;
        this.pathChannelSid = pathChannelSid;
        this.pathSid = pathSid;
    }

    /**
     * The SID of the [Role](https://www.twilio.com/docs/api/chat/rest/roles) to
     * assign to the member. The default roles are those specified on the
     * [Service](https://www.twilio.com/docs/chat/api/services)..
     *
     * @param roleSid The SID of the Role to assign to the member
     * @return this
     */
    public MemberUpdater setRoleSid(final String roleSid) {
        this.roleSid = roleSid;
        return this;
    }

    /**
     * The index of the last
     * [Message](https://www.twilio.com/docs/api/chat/rest/messages) that the Member
     * has read within the
     * [Channel](https://www.twilio.com/docs/api/chat/rest/channels)..
     *
     * @param lastConsumedMessageIndex The index of the last consumed Message for
     *                                 the Channel for the Member
     * @return this
     */
    public MemberUpdater setLastConsumedMessageIndex(final Integer lastConsumedMessageIndex) {
        this.lastConsumedMessageIndex = lastConsumedMessageIndex;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the update.
     *
     * @param client TwilioRestClient with which to make the request
     * @return Updated Member
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public Member update(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            Domains.IPMESSAGING.toString(),
            "/v1/Services/" + this.pathServiceSid + "/Channels/" + this.pathChannelSid + "/Members/" + this.pathSid + "",
            client.getRegion()
        );

        addPostParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("Member update failed: Unable to connect to server");
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

        return Member.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (roleSid != null) {
            request.addPostParam("RoleSid", roleSid);
        }

        if (lastConsumedMessageIndex != null) {
            request.addPostParam("LastConsumedMessageIndex", lastConsumedMessageIndex.toString());
        }
    }
}