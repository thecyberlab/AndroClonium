# AndroClonium

This project was done for detection of code clones in obfuscated Android apps. The key idea behind this project was too analyze execution traces of an Android app instead of the code itself for code clone detection. The challenge with this idea is that it normally means performing dynamic analysis on Android apps which is very expensive and hard to automate. So to solve this challenge we created a "Smali Simulator" which takes Smali code as input and produces possible execution traces of an Android app. Next we take these traces and analyze them for code clone detection using our "Execution Trace Analyzer". The following document explains how each of these components should be used.


## Smali Simulator
This component is an Android application itself and can be installed on an Android phone or a virtual one (emulator). For this work we used Genymotion as our emulator. The SmaliSimulator exposes some APIs over the network which we can interact with for performing smali code simulation. Note that even though accessing these APIs does show a webpage but they are intended for debugging purposes and for working with this tool you need to write a script to interact with this tool so that workflow can be automated. 

When performing Smali simulation on different Smali methods we noticed that Android apps often come with many unused methods which are not invoked during the runtime and thus should not simulated. To solve this issue we added a functionality to the Simulator that analyzes Smali code and extracts possible entry points to an application. This functionality is logically separate from the Simulation logic and should be performed as the first step in analzing an application and is needed to only analyze the relevant. 


Here are the APIs that are exposed by the simulator. Note that there maybe some APIs in the code that were used for legacy reasons or debugging purposes that no longer are needed. Here those APIs are not mentioned.

#### POST /uploadSmaliFile
This API is used to upload resource files of an application to the Android device that is running our simulator. 

**This API needs to variables to be set in the POST request body payload :`appName` and `appVersion`**

#### POST /uploadResFile
This API is used to upload Smali files to the Android device that is running our simulator. This API needs to variables to be set in the POST request body payload :`appName` and `appVersion`

**This API needs to variables to be set in the POST request body payload :`appName` and `appVersion`**

**This API is for now only used for extracting entry points of an Android application. Extracting entry points logic only needs the `layout.xml` file and other resources are not needed. This API can be used in future if further analysis on resources was added**

####  GET /getAllEntryPointsOfApp/{appName}/{appVersion}
This API analyzes the Smali code of an application and responds with the list of possible entry point methods of an app. Note that this API can take some time to analyze an app.

#### POST /simulateMethod
This method simulates the execution of a given method.

**This API needs to variables to be set in the POST request body: `filePath` and `methodSig`. Note that the filePath is file path of the Smali file on our device and should be in this format: `uploadedSmaliFiles/{appName}/{appVersion}/{SmaliFilePath}`**


### Very important points when simulating apps
* To simulate a group of apps you first need to extract their entry points and then simulate them and this is better to be done on different passes meaning first get all entry points of an app and then simulate all entry point of the apps.

* After you extract the entry points of an app or simulate their methods, before you go to the next app you need to kill the SmaliSimulator app and restart it. This is because when Simulating an App, its classes are dynamically loaded into our simulator but unfortunately there is no way to unload classes, so we need to kill the simulator instance and rerun it. To do this use these ADB commands. Here is a python snippet for doing it:

	
		def restartTool():
	    	os.system("adb -s " + adb_device_ip + " shell am force-stop usask.cyberlab.smalisimulator")
 	   		time.sleep(0.4)
	    	os.system("adb -s " + adb_device_ip + " shell am start -n usask.cyberlab.smalisimulator/usask.cyberlab.smalisimulator.MainActivity") 
 	   		time.sleep(2)


* After each simulation the results are stored on the Android device and for furhter analysis they need to be fetched. To do this again you need to use ADB. Here is a snippet for it.

		command = "adb -s " + adb_device_ip + " pull /data/data/usask.cyberlab.smalisimulator/files/traceAnalysisResults/" + appName + "_" + appVersion + ' "' + localOutputPath + '"'
	    subprocess.check_output(command, shell=True)

* After analyzing multiple apps be careful that the device storage does not get full. You need to clean the uploaded and intermediate files every now and then.

## Execution Trace Analyzer

After analyzing our Android applications and getting the possible execution traces of the methods that we have simulated, we need to analyze the execution traces for code clone detection. This component is written in Java and reads the files that have been fetched from our Simulator on to our local device. 

To run this code you need just to run the main method. The path of the directory holding the execution traces are specified by the value in the `inputExecutionTracesDirPath` field. This code first performs nested execution extraction and copy nested executions so that they would also get analyzed separately. Next we analyze these execution traces to produce slices and fragments which will be used for code clone detection later. This two logics can run after each other or you can run them in different passes meaning first extracting execution traces for all apps and then analyzing them for all apps in a separate pass. This can be done just by commenting some lines of code.

Note that this tool does not detect code clones by itself and only converts execution traces to fragments and slices. To detect code clones we wrote a python script which would read them and compare them separately.