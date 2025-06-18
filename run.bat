:: run.bat
:: Script para executar a aplicação JavaFX compilada

@echo off
setlocal

:: Define o caminho para o JavaFX SDK
set PATH_TO_FX="lib\javafx-sdk-21\lib"
set APP_MAIN_CLASS=com.mkcalculadora.CalculadoraMTM

echo Executando a aplicação...

java --module-path %PATH_TO_FX%;out --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media -cp out %APP_MAIN_CLASS%

if %ERRORLEVEL% NEQ 0 (
    echo Erro na execução.
    exit /b %ERRORLEVEL%
)

echo Aplicação encerrada.
endlocal
