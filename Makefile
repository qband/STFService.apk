init:
	scripts/init.sh

release:
	./gradlew assembleRelease

deploy:
	scripts/deploy.sh

clean:
	adb shell pm uninstall jp.co.cyberagent.stf