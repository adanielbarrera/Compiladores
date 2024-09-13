@echo off
del %1.cm1
java musica %1
if errorlevel 1 goto final
java RD %1.cm1
:final
del %1.cm1