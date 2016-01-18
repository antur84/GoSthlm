#connect real phone to watch emulator
adb -d forward tcp:5601 tcp:5601

# Connect real phone and real watch
adb forward tcp:4444 localabstract:/adb-hub

adb connect 127.0.0.1:4444
