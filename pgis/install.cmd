REM https://www.lfd.uci.edu/~gohlke/pythonlibs/
REM https://stackoverflow.com/questions/70574803/should-i-use-pipwin-or-pip-for-installing-a-library

call python -m pip install --upgrade pip
call python -m pip install pipwin
REM call python -m pip install lib\GDAL-3.4.3-cp310-cp310-win_amd64.whl
REM call python -m pip install lib\Fiona-1.8.21-cp310-cp310-win_amd64.whl
call pipwin install gdal
call pipwin install fiona
call python -m pip install matplotlib
call python -m pip install geopandas
call python -m pip install PythonTurtle
REM call python -m pip install jupyterlab
REM python -m pip install speechrecognition
REM python -m pip install pyaudio
REM python -m pip install pyttsx3