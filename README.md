# Android Navigation App (nav)

Este es un proyecto de Android diseñado para demostrar la implementación de un flujo de navegación moderno utilizando las mejores prácticas de desarrollo, incluyendo **MVVM Architecture**, **Material Design 3**, **View Binding** y **Navigation Component**.

## 📱 Capturas de Pantalla

### Login
<a href="./Screenshots/Login.png"><img src="./Screenshots/Login.png" width="180"></a>
<a href="./Screenshots/LoginValidation.png"><img src="./Screenshots/LoginValidation.png" width="180"></a>
<a href="./Screenshots/LoginComplete.png"><img src="./Screenshots/LoginComplete.png" width="180"></a>

### Register
<a href="./Screenshots/Register.png"><img src="./Screenshots/Register.png" width="180"></a>
<a href="./Screenshots/RegisterValidation.png"><img src="./Screenshots/RegisterValidation.png" width="180"></a>
<a href="./Screenshots/RegisterComplete.png"><img src="./Screenshots/RegisterComplete.png" width="180"></a>
<a href="./Screenshots/Succes.png"><img src="./Screenshots/Succes.png" width="180"></a>


## 🚀 Características

- **Arquitectura MVVM**: Separación clara entre la lógica de negocio (`ViewModel`), el estado de la UI (`Model/UIState`) y la vista (`Fragment`).
- **Validación en Tiempo Real**: Formularios de Login y Registro con validaciones dinámicas que habilitan/deshabilitan el botón de acción y muestran errores en los campos.
- **Single Activity Architecture**: Utiliza una única actividad principal (`MainActivity`) que gestiona el flujo de fragmentos.
- **Navigation Component**: Implementación robusta de navegación mediante `NavHostFragment` y `nav_graph`.
- **Material Design 3**: Interfaz de usuario moderna y adaptable con componentes avanzados de Material.
- **View Binding**: Acceso seguro a las vistas eliminando el riesgo de errores `NullPointerException` y `findViewById`.

## 🛠️ Tecnologías y Librerías

- **Kotlin**: Lenguaje principal de desarrollo.
- **Coroutines & Flow**: Para el manejo de estados asíncronos y reactivos de la UI.
- **Navigation Component**: Para la gestión de rutas y transiciones entre pantallas.
- **Material Components**: Para elementos de UI avanzados y temas.
- **ConstraintLayout**: Para diseños flexibles y eficientes.
- **View Binding**: Para la vinculación de vistas de forma segura.

## 📋 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **Android Studio Jellyfish** (o superior).
- **JDK 17** o superior.
- **Android SDK** (API 34+ recomendado).

## ⚙️ Instalación y Configuración

Sigue estos pasos para levantar el proyecto localmente:

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/pablocelva/nav.git
   ```

2. **Importar en Android Studio:**
   - Abre Android Studio.
   - Selecciona **File > Open**.
   - Navega hasta la carpeta del proyecto y haz clic en **OK**.

3. **Sincronizar Gradle:**
   - Una vez abierto el proyecto, haz clic en el botón **"Sync Project with Gradle Files"** (el icono del elefante en la barra superior).

4. **Ejecutar la aplicación:**
   - Conecta un dispositivo físico o inicia un emulador.
   - Haz clic en el botón verde de **Run** (Play).

## 📂 Estructura del Proyecto

```text
app/src/main/
├── java/com/example/nav/
│   ├── model/              # Estados de UI (UIState) y validadores
│   ├── viewModel/          # Lógica de negocio y gestión de estado
│   ├── view/               # Fragmentos (Login, Register, Home, etc.)
│   └── MainActivity.kt     # Actividad principal (Host de navegación)
├── res/
│   ├── layout/             # Diseños XML con View Binding
│   ├── navigation/         # Grafo de navegación (nav_graph.xml)
│   └── values/             # Temas, Colores y Strings
└── AndroidManifest.xml      # Configuración de la aplicación
```

## 🤝 Contribución

Si deseas contribuir:
1. Haz un **Fork** del proyecto.
2. Crea una rama para tu característica (`git checkout -b feature/NuevaMejora`).
3. Haz un **Commit** de tus cambios (`git commit -m 'Añade nueva funcionalidad'`).
4. Haz **Push** a la rama (`git push origin feature/NuevaMejora`).
5. Abre un **Pull Request**.

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - mira el archivo [LICENSE](LICENSE) para detalles.
