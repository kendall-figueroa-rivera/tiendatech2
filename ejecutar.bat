@echo off
echo ========================================
echo TiendaTech2 - Iniciando Aplicacion
echo ========================================
echo.

REM Verificar si Maven esta instalado
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven no esta instalado o no esta en el PATH
    echo Por favor instala Maven desde: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

REM Verificar si Java esta instalado
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java no esta instalado o no esta en el PATH
    echo Por favor instala Java JDK 17 o superior
    echo.
    pause
    exit /b 1
)

echo [1/3] Limpiando proyecto...
call mvn clean -q

echo.
echo [2/3] Compilando proyecto...
call mvn package -DskipTests -q

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La compilacion fallo. Revisa los errores anteriores.
    pause
    exit /b 1
)

echo.
echo [3/3] Iniciando aplicacion...
echo.
echo ========================================
echo La aplicacion estara disponible en:
echo http://localhost:8080
echo.
echo Consola H2 disponible en:
echo http://localhost:8080/h2-console
echo   URL: jdbc:h2:mem:tiendatech2
echo   Usuario: sa
echo   Password: (vacio)
echo.
echo Usuarios de prueba:
echo   Admin: admin@tiendatech.com / admin123
echo   Usuario: juan@example.com / user123
echo ========================================
echo.
echo Abriendo navegador en 5 segundos...
timeout /t 5 /nobreak >nul

REM Abrir navegador automaticamente
start http://localhost:8080

echo.
echo Iniciando aplicacion...
echo.

REM Iniciar la aplicacion en una nueva ventana
start "TiendaTech2 - Servidor" cmd /k "java -jar target\tiendatech2-0.0.1-SNAPSHOT.jar"

REM Esperar a que la aplicacion inicie
echo Esperando a que la aplicacion inicie (15 segundos)...
timeout /t 15 /nobreak >nul

REM Abrir navegador automaticamente
echo Abriendo navegador...
start http://localhost:8080

echo.
echo ========================================
echo Aplicacion iniciada correctamente!
echo Navegador abierto en http://localhost:8080
echo ========================================
echo.
echo La aplicacion se esta ejecutando en otra ventana.
echo Para detener la aplicacion, cierra la ventana del servidor.
echo.
pause
