if exist src/main/resources/static/* (
    del /f /s /q /a "src/main/resources/static"
)
cd frontend && npm run build && cd .. && gradlew bootWar && del /f /s /q /a "src/main/resources/static"
