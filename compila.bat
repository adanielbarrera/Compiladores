@echo off
del %1.cm1
javac musica.java
java musica %1
::java musica crea un archivo prueba.cm1
pause
if errorlevel 1 goto final
pause
javac sintaxis.java
java Sintaxis.java %1.cm1
::aqui usamos el recursivo descendente para revisar la sintaxis del programa
:final
del %1.cm1