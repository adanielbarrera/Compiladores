@echo off
del %1.cm1
java musica %1
if errorlevel 1 goto final
pause
javac Sintaxis.java
java Sintaxis %1
:final
del %1.cm1