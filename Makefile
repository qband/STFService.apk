init:
	scripts/init.sh

release:
	./gradlew assembleRelease

install:
	adb install app/build/outputs/apk/app-release.apk

deploy:
	scripts/deploy.sh

clean:
	adb shell pm uninstall jp.co.cyberagent.stf