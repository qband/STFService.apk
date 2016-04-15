ENV ?= Debug
FILE=
ifeq "$(ENV)" "Debug"
FILE = app/build/outputs/apk/app-debug.apk
else
FILE = app/build/outputs/apk/app-release.apk
endif

init:
	scripts/init.sh

apk:
	./gradlew assemble$(ENV)

install:
	adb install $(FILE)

deploy:
	scripts/deploy.sh

clean:
	adb shell pm uninstall jp.co.cyberagent.stf

log:
	adb logcat -c
	adb logcat | tee adb.log