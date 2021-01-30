package ai.fedml.fedmlsdk.trainingexecutor;

import com.google.gson.annotations.SerializedName;

public class ExecutorResponse {
    @SerializedName("errno")
    private String errno;
    @SerializedName("executorId")
    private String executorId;
    @SerializedName("executorTopic")
    private String executorTopic;
    @SerializedName("client_id")
    private String clientId;
    @SerializedName("training_task_args")
    private TrainingTaskParam trainingTaskArgs;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public TrainingTaskParam getTrainingTaskArgs() {
        return trainingTaskArgs;
    }

    public void setTrainingTaskArgs(TrainingTaskParam trainingTaskArgs) {
        this.trainingTaskArgs = trainingTaskArgs;
    }


}
