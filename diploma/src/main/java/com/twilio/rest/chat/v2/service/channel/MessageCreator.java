/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.chat.v2.service.channel;

import com.twilio.base.Creator;
import com.twilio.converter.DateConverter;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;
import org.joda.time.DateTime;

public class MessageCreator extends Creator<Message> {
    private final String pathServiceSid;
    private final String pathChannelSid;
    private String from;
    private String attributes;
    private DateTime dateCreated;
    private DateTime dateUpdated;
    private String lastUpdatedBy;
    private String body;
    private String mediaSid;

    /**
     * Construct a new MessageCreator.
     *
     * @param pathServiceSid The SID of the Service to create the resource under
     * @param pathChannelSid The SID of the Channel the new resource belongs to
     */
    public MessageCreator(final String pathServiceSid,
                          final String pathChannelSid) {
        this.pathServiceSid = pathServiceSid;
        this.pathChannelSid = pathChannelSid;
    }

    /**
     * The [Identity](https://www.twilio.com/docs/chat/identity) of the new
     * message's author. The default value is `system`..
     *
     * @param from The Identity of the new message's author
     * @return this
     */
    public MessageCreator setFrom(final String from) {
        this.from = from;
        return this;
    }

    /**
     * A valid JSON string that contains application-specific data..
     *
     * @param attributes A valid JSON string that contains application-specific data
     * @return this
     */
    public MessageCreator setAttributes(final String attributes) {
        this.attributes = attributes;
        return this;
    }

    /**
     * The date, specified in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601)
     * format, to assign to the resource as the date it was created. The default
     * value is the current time set by the Chat service. This parameter should only
     * be used when a Chat's history is being recreated from a backup/separate
     * source..
     *
     * @param dateCreated The ISO 8601 date and time in GMT when the resource was
     *                    created
     * @return this
     */
    public MessageCreator setDateCreated(final DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * The date, specified in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601)
     * format, to assign to the resource as the date it was last updated..
     *
     * @param dateUpdated The ISO 8601 date and time in GMT when the resource was
     *                    updated
     * @return this
     */
    public MessageCreator setDateUpdated(final DateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    /**
     * The [Identity](https://www.twilio.com/docs/chat/identity) of the User who
     * last updated the Message, if applicable..
     *
     * @param lastUpdatedBy The Identity of the User who last updated the Message
     * @return this
     */
    public MessageCreator setLastUpdatedBy(final String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    /**
     * The message to send to the channel. Can be an empty string or `null`, which
     * sets the value as an empty string. You can send structured data in the body
     * by serializing it as a string..
     *
     * @param body The message to send to the channel
     * @return this
     */
    public MessageCreator setBody(final String body) {
        this.body = body;
        return this;
    }

    /**
     * The SID of the [Media](https://www.twilio.com/docs/chat/rest/media) to attach
     * to the new Message..
     *
     * @param mediaSid  The Media Sid to be attached to the new Message
     * @return this
     */
    public MessageCreator setMediaSid(final String mediaSid) {
        this.mediaSid = mediaSid;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the create.
     *
     * @param client TwilioRestClient with which to make the request
     * @return Created Message
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public Message create(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            Domains.CHAT.toString(),
            "/v2/Services/" + this.pathServiceSid + "/Channels/" + this.pathChannelSid + "/Messages",
            client.getRegion()
        );

        addPostParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("Message creation failed: Unable to connect to server");
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

        return Message.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (from != null) {
            request.addPostParam("From", from);
        }

        if (attributes != null) {
            request.addPostParam("Attributes", attributes);
        }

        if (dateCreated != null) {
            request.addPostParam("DateCreated", dateCreated.toString());
        }

        if (dateUpdated != null) {
            request.addPostParam("DateUpdated", dateUpdated.toString());
        }

        if (lastUpdatedBy != null) {
            request.addPostParam("LastUpdatedBy", lastUpdatedBy);
        }

        if (body != null) {
            request.addPostParam("Body", body);
        }

        if (mediaSid != null) {
            request.addPostParam("MediaSid", mediaSid);
        }
    }
}