Pre-requisites 
Gradle
Java
instacart account credentials
Make sure to add items from only one store in the cart
Add card details in advance
Add delivery address in advance
Make sure to TURN UP the system volume

Steps:
1. Check out the repo
2. Open build.gradle in IntelliJ (Open as Project).. should be similar in eclipse as well
3. update your instacart user name and password and chromedriver path
    a. Downlaod chromdriver compatiable for your chrome browser version (https://chromedriver.chromium.org/downloads)
    b. Update the chromedriver path in https://github.com/gunaphysics/InstaCartDeliveryCheck/blob/master/src/main/java/Main.java#L19
    c. This code was tested on both macOS Catalina and Windows 10 with 
        Chrome Version 80.0.3987.163 (Official Build) (64-bit)
        Chromedriver Version : https://chromedriver.storage.googleapis.com/index.html?path=80.0.3987.16/
4. Run the Main.java file (main method)

As soon as a slot is available, the program will tigger beep sounds for 40 seconds..

During this time, select the slot and commplete the delivery within the browser window itself. Do not try with a new session/browser.


