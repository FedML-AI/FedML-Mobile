package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MultiLayerNetwork lrNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");
        TextView testResult = findViewById(R.id.textView);
        testResult.setVisibility(View.INVISIBLE);

        //define the layers of the network
        OutputLayer outputLayer = new OutputLayer.Builder(LossFunctions.LossFunction.XENT)
                .nIn(784)
                .nOut(10)
                .name("Output")
                .activation(Activation.SIGMOID)
                .build();
        //Build network
        NeuralNetConfiguration.Builder nncBuilder = new NeuralNetConfiguration.Builder();
        int seed = 123;
        nncBuilder.seed(seed);
        nncBuilder.updater(new Nesterovs(0.1, 0.9));
        nncBuilder.weightInit(WeightInit.XAVIER);
        NeuralNetConfiguration.ListBuilder listBuilder = nncBuilder.list();
        listBuilder.layer(0, outputLayer);
        lrNetwork = new MultiLayerNetwork(listBuilder.build());
        lrNetwork.init();
        Log.d(TAG, "Model built");

        //Randomly generate 4 batches out of 1000 datasets
        Random generator = new Random();
        int[] miniBatch = new int[4];
        for (int i = 0; i < 4; i++) {
            miniBatch[i] = generator.nextInt(1000);
        }
        for (int epoch = 0; epoch < 10; epoch++) {
            for (int batch = 0; batch < 4; batch++) {
                //Dataset preparation
                String userName = "f_00" + String.format("%03d", miniBatch[batch]);
                String testPath = this.getFilesDir() + "/MNIST_mobile/" + miniBatch[batch] + "/test";
                String trainPath = this.getFilesDir() + "/MNIST_mobile/" + miniBatch[batch] + "/train";
                // Get the input data for training
                INDArray inputs = dataLoader(trainPath, "train.json", userName, true);
                // Get the label for training
                INDArray outputs = dataLoader(trainPath, "train.json", userName, false);
                // Get the input data for evaluation
                INDArray evalInputs = dataLoader(testPath, "test.json", userName, true);
                // Get the label for evaluation
                INDArray evalOutputs = dataLoader(testPath, "test.json", userName, false);

                //For code testing
                Log.d(TAG, "Started training");
                TrainingRunner trainingRunner = new TrainingRunner(inputs, outputs, evalInputs, evalOutputs);
                trainingRunner.execute();
            }
        }

    }

    // Load data from files
    private INDArray dataLoader(String dirPath, String fileName, String userName, boolean isInput) {
        INDArray data = null;
        double[][] dataSet = null;
        try {
            File file = new File(dirPath, fileName);
            FileReader fileReader = new FileReader(file);
            HashMap<String, Object> trainData = new Gson().fromJson(fileReader, HashMap.class);
            LinkedTreeMap<String, Object> userData = (LinkedTreeMap<String, Object>) trainData.get("user_data");
            LinkedTreeMap<String, Object> userDataDeep = (LinkedTreeMap<String, Object>) userData.get(userName);
            if (isInput) {
                ArrayList<ArrayList<Double>> x_train = (ArrayList<ArrayList<Double>>) userDataDeep.get("x");
                // Pre-treat data into NDArray that can be fit into DL4J model
                dataSet = new double[x_train.size()][784];
                //treat x
                for (int i = 0; i < x_train.size(); i++) {
                    for (int j = 0; j < 784; j++) {
                        dataSet[i][j] = x_train.get(i).get(j);
                    }
                }
            }
            else {
                ArrayList<Double> y_train = (ArrayList<Double>) userDataDeep.get("y");
                // Pre-treat data into NDArray that can be fit into DL4J model
                dataSet = new double[y_train.size()][10];
                //treat y
                for(int i = 0; i < y_train.size(); i++) {
                    int label = y_train.get(i).intValue();
                    dataSet[i][label] = 1.0;
                }
            }
            data = Nd4j.create(dataSet);
        }
        catch (IOException e) {
            Log.d(TAG, "File not found");
            finish();
        }
        return data;
    }

    private class TrainingRunner extends AsyncTask<INDArray, Integer, String>{

        private INDArray inputs;
        private INDArray outputs;
        private INDArray evalInputs;
        private INDArray evalOutputs;

        public TrainingRunner(INDArray input, INDArray output, INDArray evalInput, INDArray evalOutput) {
            inputs = input;
            outputs = output;
            evalInputs = evalInput;
            evalOutputs = evalOutput;
        }

        @Override
        protected String doInBackground(INDArray... indArrays) {
            //Start training
            lrNetwork.fit(inputs, outputs);
            Log.d(TAG, "Training finished");
            //Evaluation
            INDArray testResult = lrNetwork.output(evalInputs);
            Evaluation eval = new Evaluation(10);
            eval.eval(evalOutputs, testResult);
            Log.d(TAG, "Accuracy: " + eval.accuracy()+ "");
            return eval.accuracy()+"";
        }

    }
}

