package com.filreas.gosthlm.datalayer;

import android.content.Context;
import android.widget.Toast;

import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;
import com.filreas.shared.utils.DataLayerUri;
import com.filreas.shared.utils.DtoSerializer;
import com.filreas.shared.utils.GoSthlmLog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableStatusCodes;

import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.util.List;

public class WearActions {

    private final GoogleApiClient client;
    private final Context context;

    public WearActions(GoogleApiClient client, Context context) {
        this.client = client;
        this.context = context;
    }

    public void sendFavouriteSiteUpdate(FavouriteSiteLiveUpdateDto site) {
        GoSthlmLog.d("sendFavouriteSiteUpdate " + site.getName());
        updateTimestamp(site);
        PutDataRequest request = PutDataRequest.create(DataLayerUri.FAVOURITE_SITE_UPDATE);
        sendToPhone(request, site);
    }

    public void notifyAllFavouriteSitesUpdated() {
        Wearable.NodeApi.getConnectedNodes(client).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                List<Node> nodes = getConnectedNodesResult.getNodes();
                GoSthlmLog.d("notifyAllFavouriteSitesUpdated: " + nodes.size());
                for (Node node : nodes) {
                    Wearable.MessageApi.sendMessage(client, node.getId(), DataLayerUri.REFRESH_ALL_DATA_ON_WATCH_COMPLETED, null);
                }
            }
        });
    }

    public void notifyFavouriteSiteUpdateFailed(FavouriteSiteLiveUpdateDto site) {
        GoSthlmLog.d("notifyFavouriteSiteUpdateFailed " + site.getName());
        updateTimestamp(site);
        PutDataRequest request = PutDataRequest.create(DataLayerUri.FAVOURITE_SITE_UPDATE_FAILED);
        sendToPhone(request, site);
    }

    private void updateTimestamp(FavouriteSiteLiveUpdateDto site) {
        site.setLastUpdatedInMillies(LocalDateTime.now().getMillisOfDay());
    }

    private void sendToPhone(PutDataRequest request, Object serializableItem) {
        try {
            trySendToPhone(request, serializeOrThrow(serializableItem));
        } catch (IOException ignored) {
        }
    }

    private byte[] serializeOrThrow(Object serializableItem) throws IOException {
        try {
            return DtoSerializer.convertToBytes(serializableItem);
        } catch (IOException e) {
            GoSthlmLog.e(e);
            Toast.makeText(
                    context,
                    "Error serializing data before sending to phone",
                    Toast.LENGTH_SHORT).show();
            throw e;
        }
    }

    private void trySendToPhone(PutDataRequest request, byte[] data) {
        request.setData(data);
        PendingResult<DataApi.DataItemResult> result = Wearable.DataApi.putDataItem(
                client,
                request);
        result.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {
                if (dataItemResult.getStatus().isSuccess()) {
                    GoSthlmLog.d("Update pushed successfully");
                } else {
                    GoSthlmLog.d("Update failed to push, statusCode: "
                            + dataItemResult.getStatus().getStatusCode() +
                            ", msg: " + WearableStatusCodes.getStatusCodeString(
                            dataItemResult.getStatus().getStatusCode()));
                }
            }
        });
    }
}
