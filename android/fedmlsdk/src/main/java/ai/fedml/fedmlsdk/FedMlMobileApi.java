package ai.fedml.fedmlsdk;

import android.content.Context;

import ai.fedml.fedmlsdk.listeners.FedMlTaskListener;
import ai.fedml.fedmlsdk.listeners.OnRegisterListener;
import androidx.annotation.NonNull;

public interface FedMlMobileApi {
    /**
     * initialize FedMl Mobile Device
     *
     * @param context  Context
     * @param baseUrl  Executor
     * @param broker   broker server:port
     * @param listener OnRegisterListener
     */
    void init(@NonNull final Context context, @NonNull final String baseUrl,
              @NonNull final String broker, @NonNull final OnRegisterListener listener);

    /**
     * upload files,such as models.
     *
     * @param fileName the file name
     * @param filePath the file path
     * @return
     */
    void uploadFile(@NonNull final String fileName, @NonNull final String filePath);

    /**
     * download files,such as the data set.
     *
     * @param dataSetName the name of data set
     * @param fileName    the name to save
     * @param url         the utl of the file
     */
    void downloadUnzipDataSet(@NonNull String dataSetName, @NonNull final String fileName, @NonNull final String url);

    /**
     * send message to executor
     *
     * @param msg the message
     */
    void sendMessage(@NonNull String msg);
}
