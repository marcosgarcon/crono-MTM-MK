:: package_installer.bat
:: Script para gerar o instalador .exe usando jpackage

@echo off
setlocal

:: Define o caminho para o JavaFX SDK
set PATH_TO_FX="lib\javafx-sdk-21\lib"
set APP_MAIN_CLASS=com.mkcalculadora.CalculadoraMTM
set APP_NAME="MK Calculadora MTM"
set APP_VERSION="1.0"
set APP_ICON="resources\mk_logo.ico" :: Certifique-se de que este arquivo existe

echo Gerando o instalador .exe com jpackage...

:: Certifique-se de que o diretório 'out' (com os .class compilados) existe
if not exist out (
    echo O diretório 'out' não foi encontrado. Por favor, execute build.bat primeiro.
    exit /b 1
)

:: Cria um JAR executável primeiro
echo Criando JAR executável...
jar --create --file out\MKCalculadoraMTM.jar --main-class %APP_MAIN_CLASS% -C out .

if %ERRORLEVEL% NEQ 0 (
    echo Erro ao criar o JAR.
    exit /b %ERRORLEVEL%
)

:: Limpa o diretório 'dist' se existir, para uma nova geração limpa
if exist dist rmdir /s /q dist

jpackage ^
    --input out ^
    --name %APP_NAME% ^
    --main-jar MKCalculadoraMTM.jar ^
    --main-class %APP_MAIN_CLASS% ^
    --type installer ^
    --dest dist ^
    --java-options "-Xmx512m" ^
    --module-path %PATH_TO_FX% ^
    --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media ^
    --resource-dir resources ^
    --icon %APP_ICON% ^
    --app-version %APP_VERSION% ^
    --vendor "Marcos Garçon" ^
    --copyright "Copyright (c) 2024 Marcos Garçon" ^
    --win-dir-chooser ^
    --win-shortcut ^
    --win-menu

if %ERRORLEVEL% NEQ 0 (
    echo Erro ao gerar o instalador.
    exit /b %ERRORLEVEL%
)

echo Instalador .exe gerado com sucesso em dist\%APP_NAME%-%APP_VERSION%.exe
endlocal
