package ai.fedml.fedmlsdk.listeners;

import ai.fedml.fedmlsdk.trainingexecutor.TrainingTaskParam;

public interface OnRegisterListener {
    void onRegisterReceived(final String executorId, final TrainingTaskParam param);
}
