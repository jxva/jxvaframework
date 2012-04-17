@echo off&setlocal enabledelayedexpansion

set BACK_PATH=F:\backup\codeblocks_workspace

set ft=%time:~0,2%_%time:~3,2%_%time:~6,2%

set %ft: =0% 


set include_files=.

set exclude_files=-x*\.svn -x*\*.exe -x*\*.o -x*\bin -x*\obj

cd /d E:\workspace_c

del *.stackdump

if not exist %BACK_PATH%  md %BACK_PATH%

set BACK_FILE=%BACK_PATH%\c_workspace_%date:~0,10%_%ft%.7z

winrar a  "%BACK_FILE%" %include_files% %exclude_files% -r -hppass1009

copy "%BACK_FILE%" X:\daily
