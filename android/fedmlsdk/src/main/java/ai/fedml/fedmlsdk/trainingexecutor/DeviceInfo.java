package ai.fedml.fedmlsdk.trainingexecutor;

import com.google.gson.annotations.SerializedName;

public class DeviceInfo {
    @SerializedName("deviceId")
    private String deviceId;

    public DeviceInfo(final String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String deviceId;

        public Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public DeviceInfo build() {
            return new DeviceInfo(this.deviceId);
        }
    }
}
