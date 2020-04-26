/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.taskrouter.v1.workspace.worker;

import com.twilio.base.Updater;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

public class WorkerChannelUpdater extends Updater<WorkerChannel> {
    private final String pathWorkspaceSid;
    private final String pathWorkerSid;
    private final String pathSid;
    private Integer capacity;
    private Boolean available;

    /**
     * Construct a new WorkerChannelUpdater.
     *
     * @param pathWorkspaceSid The SID of the Workspace with the WorkerChannel to
     *                         update
     * @param pathWorkerSid The SID of the Worker with the WorkerChannel to update
     * @param pathSid The SID of the WorkerChannel to update
     */
    public WorkerChannelUpdater(final String pathWorkspaceSid,
                                final String pathWorkerSid,
                                final String pathSid) {
        this.pathWorkspaceSid = pathWorkspaceSid;
        this.pathWorkerSid = pathWorkerSid;
        this.pathSid = pathSid;
    }

    /**
     * The total number of Tasks that the Worker should handle for the TaskChannel
     * type. TaskRouter creates reservations for Tasks of this TaskChannel type up
     * to the specified capacity. If the capacity is 0, no new reservations will be
     * created..
     *
     * @param capacity The total number of Tasks that the Worker should handle for
     *                 the TaskChannel type
     * @return this
     */
    public WorkerChannelUpdater setCapacity(final Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    /**
     * Whether the WorkerChannel is available. Set to `false` to prevent the Worker
     * from receiving any new Tasks of this TaskChannel type..
     *
     * @param available Whether the WorkerChannel is available
     * @return this
     */
    public WorkerChannelUpdater setAvailable(final Boolean available) {
        this.available = available;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the update.
     *
     * @param client TwilioRestClient with which to make the request
     * @return Updated WorkerChannel
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public WorkerChannel update(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            Domains.TASKROUTER.toString(),
            "/v1/Workspaces/" + this.pathWorkspaceSid + "/Workers/" + this.pathWorkerSid + "/Channels/" + this.pathSid + "",
            client.getRegion()
        );

        addPostParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("WorkerChannel update failed: Unable to connect to server");
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

        return WorkerChannel.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (capacity != null) {
            request.addPostParam("Capacity", capacity.toString());
        }

        if (available != null) {
            request.addPostParam("Available", available.toString());
        }
    }
}