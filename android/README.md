# OnDeviceTraining

This is the source of the android project used to the mobile on-device training of shallow neural networks and conventional ML models such as LR and SVM.


## How to run

The project is built using Android Studio, so the simplest way to run it is importing it to Android Studio and re-building it.

Android SDK = 27
Android Studio = 4.0.1


## Code Architecture
fedmlmobile -> fedml-iot-sdk -> fedmlsdk

fedmlsdk: used to communicate Communication with  FedMLServer
fedml-iot-sdk: Run a Android background Service in sub-process，mplement all of the training logic.
fedmlmobile: The Host App of fedml-iot-sdk which carries all  capabilites of FedML.