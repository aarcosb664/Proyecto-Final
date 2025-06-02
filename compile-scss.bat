@echo off
echo Compilando archivos SCSS...

:: Directorio base
set SCSS_DIR=src\main\resources\static\scss
set CSS_DIR=src\main\resources\static\css

:: Compilar archivos principales
sass %SCSS_DIR%\general.scss %CSS_DIR%\general.css --style=compressed
sass %SCSS_DIR%\index.scss %CSS_DIR%\index.css --style=compressed
sass %SCSS_DIR%\community.scss %CSS_DIR%\community.css --style=compressed
sass %SCSS_DIR%\login.scss %CSS_DIR%\login.css --style=compressed
sass %SCSS_DIR%\game.scss %CSS_DIR%\game.css --style=compressed
sass %SCSS_DIR%\ranking.scss %CSS_DIR%\ranking.css --style=compressed

echo Compilacion completada!
pause 