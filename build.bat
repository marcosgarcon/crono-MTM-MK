:: build.bat
:: Script para compilar o projeto JavaFX

@echo off
setlocal

:: Define o caminho para o JavaFX SDK
set PATH_TO_FX="lib\javafx-sdk-21\lib"
set APP_MAIN_CLASS=com.mkcalculadora.CalculadoraMTM

echo Compilando o projeto Java...

:: Cria o diretório de saída se não existir
if not exist out mkdir out

javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media -d out src\main\java\com\mkcalculadora\*.java src\module-info.java

if %ERRORLEVEL% NEQ 0 (
    echo Erro na compilação.
    exit /b %ERRORLEVEL%
)

echo Compilação concluída com sucesso!
endlocal
