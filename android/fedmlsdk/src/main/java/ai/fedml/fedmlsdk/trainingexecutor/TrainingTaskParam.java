package ai.fedml.fedmlsdk.trainingexecutor;

import com.google.gson.annotations.SerializedName;

public class TrainingTaskParam {
    @SerializedName("dataset")
    private String dataSet;
    @SerializedName("data_dir")
    private String dataDir;
    @SerializedName("partition_method")
    private String partitionMethod;
    @SerializedName("partition_alpha")
    private String partitionAlpha;
    @SerializedName("model")
    private String model;
    @SerializedName("client_num_per_round")
    private String clientNumPerRound;
    @SerializedName("comm_round")
    private String commRound;
    @SerializedName("epochs")
    private String epochs;
    @SerializedName("lr")
    private String lr;
    @SerializedName("wd")
    private String wd;
    @SerializedName("batch_size")
    private String batchSize;
    @SerializedName("frequency_of_the_test")
    private String frequencyOfTheTest;
    @SerializedName("is_mobile")
    private boolean isMobile;
    @SerializedName("dataset_url")
    private String datasetUrl;
    @SerializedName("is_preprocessed")
    private boolean isPreprocessed;

    public String getDataSet() {
        return dataSet;
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public String getPartitionMethod() {
        return partitionMethod;
    }

    public void setPartitionMethod(String partitionMethod) {
        this.partitionMethod = partitionMethod;
    }

    public String getPartitionAlpha() {
        return partitionAlpha;
    }

    public void setPartitionAlpha(String partitionAlpha) {
        this.partitionAlpha = partitionAlpha;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getClientNumPerRound() {
        return clientNumPerRound;
    }

    public void setClientNumPerRound(String clientNumPerRound) {
        this.clientNumPerRound = clientNumPerRound;
    }

    public String getCommRound() {
        return commRound;
    }

    public void setCommRound(String commRound) {
        this.commRound = commRound;
    }

    public String getEpochs() {
        return epochs;
    }

    public void setEpochs(String epochs) {
        this.epochs = epochs;
    }

    public String getLr() {
        return lr;
    }

    public void setLr(String lr) {
        this.lr = lr;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }

    public String getFrequencyOfTheTest() {
        return frequencyOfTheTest;
    }

    public void setFrequencyOfTheTest(String frequencyOfTheTest) {
        this.frequencyOfTheTest = frequencyOfTheTest;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public String getDatasetUrl() {
        return datasetUrl;
    }

    public void setDatasetUrl(String datasetUrl) {
        this.datasetUrl = datasetUrl;
    }

    public boolean isPreprocessed() {
        return isPreprocessed;
    }

    public void setPreprocessed(boolean preprocessed) {
        this.isPreprocessed = preprocessed;
    }
}
