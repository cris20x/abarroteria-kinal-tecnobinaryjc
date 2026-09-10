# Abarrotería Kinal

Aplicación de escritorio en JavaFX para gestionar usuarios y productos de una abarrotería (login, registro y dashboard).

## Stack
- Java 21 + JavaFX 21
- MySQL (mysql-connector-j)
- Proyecto Ant/NetBeans

## Estructura
```
src/main/java/...    Código fuente (paquete com.tecnobinaryjc.abarroteria.kinal)
src/main/resources/  Vistas FXML, estilos CSS e imágenes
```

## Configuración de base de datos
Editar `config/Credentials.java` con la URL, usuario y contraseña de tu MySQL local.

## Nota sobre el error de compilación
El error `NoClassDefFoundError ... wrong name` se debía a que el paquete usaba mayúsculas
inconsistentes (`TecnoBinaryJC`) frente a artefactos de compilación previos con minúsculas.
Se normalizó el paquete completo a minúsculas (`com.tecnobinaryjc...`), como indica la
convención de Java, y se eliminaron las carpetas `build/` y `dist/` (generadas por el propio
build, no deben versionarse; ya están en `.gitignore`).
