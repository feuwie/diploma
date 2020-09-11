/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.taskrouter.v1.workspace;

import com.twilio.base.Updater;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

public class WorkerUpdater extends Updater<Worker> {
    private final String pathWorkspaceSid;
    private final String pathSid;
    private String activitySid;
    private String attributes;
    private String friendlyName;
    private Boolean rejectPendingReservations;

    /**
     * Construct a new WorkerUpdater.
     *
     * @param pathWorkspaceSid The SID of the Workspace with the Worker to update
     * @param pathSid The SID of the resource to update
     */
    public WorkerUpdater(final String pathWorkspaceSid,
                         final String pathSid) {
        this.pathWorkspaceSid = pathWorkspaceSid;
        this.pathSid = pathSid;
    }

    /**
     * The SID of a valid Activity that will describe the Worker's initial state.
     * See [Activities](https://www.twilio.com/docs/taskrouter/api/activity) for
     * more information..
     *
     * @param activitySid The SID of the Activity that describes the Worker's
     *                    initial state
     * @return this
     */
    public WorkerUpdater setActivitySid(final String activitySid) {
        this.activitySid = activitySid;
        return this;
    }

    /**
     * The JSON string that describes the Worker. For example: `{ "email":
     * "Bob@example.com", "phone": "+5095551234" }`. This data is passed to the
     * `assignment_callback_url` when TaskRouter assigns a Task to the Worker.
     * Defaults to {}..
     *
     * @param attributes The JSON string that describes the Worker
     * @return this
     */
    public WorkerUpdater setAttributes(final String attributes) {
        this.attributes = attributes;
        return this;
    }

    /**
     * A descriptive string that you create to describe the Worker. It can be up to
     * 64 characters long..
     *
     * @param friendlyName A string to describe the Worker
     * @return this
     */
    public WorkerUpdater setFriendlyName(final String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    /**
     * Whether to reject pending reservations..
     *
     * @param rejectPendingReservations Whether to reject pending reservations
     * @return this
     */
    public WorkerUpdater setRejectPendingReservations(final Boolean rejectPendingReservations) {
        this.rejectPendingReservations = rejectPendingReservations;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the update.
     *
     * @param client TwilioRestClient with which to make the request
     * @return Updated Worker
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public Worker update(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            Domains.TASKROUTER.toString(),
            "/v1/Workspaces/" + this.pathWorkspaceSid + "/Workers/" + this.pathSid + "",
            client.getRegion()
        );

        addPostParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("Worker update failed: Unable to connect to server");
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

        return Worker.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (activitySid != null) {
            request.addPostParam("ActivitySid", activitySid);
        }

        if (attributes != null) {
            request.addPostParam("Attributes", attributes);
        }

        if (friendlyName != null) {
            request.addPostParam("FriendlyName", friendlyName);
        }

        if (rejectPendingReservations != null) {
            request.addPostParam("RejectPendingReservations", rejectPendingReservations.toString());
        }
    }
}