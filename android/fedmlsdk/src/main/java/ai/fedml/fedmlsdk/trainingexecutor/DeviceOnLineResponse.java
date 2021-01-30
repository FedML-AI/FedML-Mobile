package ai.fedml.fedmlsdk.trainingexecutor;

import com.google.gson.annotations.SerializedName;

public class DeviceOnLineResponse {
    @SerializedName("errno")
    private int errno;
    @SerializedName("executorId")
    private String executorId;
    @SerializedName("executorTopic")
    private String executorTopic;

    public DeviceOnLineResponse(int errno, String executorId, String executorTopic) {
        this.errno = errno;
        this.executorId = executorId;
        this.executorTopic = executorTopic;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public String getExecutorTopic() {
        return executorTopic;
    }

    public void setExecutorTopic(String executorTopic) {
        this.executorTopic = executorTopic;
    }
}
