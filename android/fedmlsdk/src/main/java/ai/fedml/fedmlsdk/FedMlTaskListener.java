package ai.fedml.fedmlsdk;

import ai.fedml.fedmlsdk.trainingexecutor.TrainingTaskParam;

public interface FedMlTaskListener {
    void onReceive(TrainingTaskParam param);
}
