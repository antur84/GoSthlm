package com.filreas.gosthlm.datalayer;

import com.filreas.shared.utils.DataLayerUri;
import com.filreas.shared.utils.GoSthlmLog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class PhoneActions {
    private final GoogleApiClient mobileClient;

    public PhoneActions(GoogleApiClient mobileClient) {
        this.mobileClient = mobileClient;
    }

    public void refreshAll() {
        Wearable.NodeApi.getConnectedNodes(mobileClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                List<Node> nodes = getConnectedNodesResult.getNodes();
                GoSthlmLog.d("refreshAll nodes: " + nodes.size());
                for (Node node : nodes) {
                    Wearable.MessageApi.sendMessage(mobileClient, node.getId(), DataLayerUri.REFRESH_ALL_DATA_ON_WATCH_REQUESTED, null);
                }
            }
        });
    }
}
