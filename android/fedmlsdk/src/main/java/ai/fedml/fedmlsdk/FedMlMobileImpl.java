package ai.fedml.fedmlsdk;

import android.content.Context;

import ai.fedml.fedmlsdk.listeners.FedMlTaskListener;
import ai.fedml.fedmlsdk.listeners.OnRegisterListener;
import androidx.annotation.NonNull;

import ai.fedml.fedmlsdk.trainingexecutor.TrainingExecutor;

class FedMlMobileImpl implements FedMlMobileApi {
    private TrainingExecutor mTrainingExecutor;

    @Override
    public void init(@NonNull Context context, @NonNull String baseUrl, @NonNull String broker,
                     @NonNull final OnRegisterListener listener) {
        ContextHolder.initialize(context);
        mTrainingExecutor = new TrainingExecutor(baseUrl, broker);
        mTrainingExecutor.init();
        mTrainingExecutor.registerDevice(listener);
    }

    @Override
    public void uploadFile(@NonNull final String fileName, @NonNull final String filePath) {
        mTrainingExecutor.uploadFile(fileName, filePath);
    }

    @Override
    public void downloadUnzipDataSet(@NonNull String dataSetName, @NonNull String fileName,
                                     @NonNull String url) {
        mTrainingExecutor.downloadUnzipDataSet(dataSetName, fileName, url);
    }

    @Override
    public void sendMessage(@NonNull String msg) {
        mTrainingExecutor.sendMessage(msg);
    }
}
