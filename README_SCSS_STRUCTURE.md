# Estructura de Archivos SCSS

## Organización

La estructura de archivos SCSS ha sido reorganizada para mayor claridad y reutilización:

### `/scss/base/`
Contiene los archivos fundamentales que se usan en todo el proyecto:

- **`_vars.scss`** - Variables globales (colores, espaciados, breakpoints)
- **`_mixins.scss`** - Mixins reutilizables
- **`_global.scss`** - Estilos globales (reset, body, html, container)
- **`_keyframes.scss`** - Animaciones keyframes globales
- **`_buttons.scss`** - Estilos base para botones
- **`_cards.scss`** - Estilos base para tarjetas
- **`_tables.scss`** - Estilos base para tablas
- **`_forms.scss`** - Estilos base para formularios
- **`_index.scss`** - Archivo índice que importa todo lo anterior

### `/scss/components/`
Componentes reutilizables:
- **`_navbar.scss`** - Barra de navegación
- **`_footer.scss`** - Pie de página
- **`_banner.scss`** - Banners
- **`_utils-freefrontend.scss`** - Utilidades externas (no modificar)

### `/scss/`
Archivos principales:
- **`general.scss`** - Estilos generales para todas las páginas
- **`index.scss`** - Estilos específicos de la página index (rombos)
- **`community.scss`** - Estilos específicos de community
- **`login.scss`** - Estilos específicos de login
- **`game.scss`** - Estilos específicos del juego
- **`ranking.scss`** - Estilos específicos de ranking

## Compilación

### Usando npm (Recomendado)

1. Instalar dependencias:
   ```bash
   npm install
   ```

2. Compilar una vez:
   ```bash
   npm run sass:build
   ```

3. Compilar y observar cambios (desarrollo):
   ```bash
   npm run sass:dev
   ```

4. Compilar y observar cambios (producción):
   ```bash
   npm run sass
   ```

### Usando el script batch (Windows)

```bash
compile-scss.bat
```

## Uso en HTML

En tus archivos HTML, incluye:

```html
<!-- Estilos generales (incluye todo lo base) -->
<link href="/css/general.css" rel="stylesheet">

<!-- Estilos específicos de la página (solo uno por página) -->
<link href="/css/index.css" rel="stylesheet"> <!-- O community.css, login.css, etc. -->
```

## Mejoras Implementadas

1. **Variables centralizadas** - Todas las variables en un solo lugar
2. **Código reutilizable** - Mixins y componentes base compartidos
3. **Estructura clara** - Separación entre general y específico
4. **Menos duplicación** - Animaciones y estilos comunes en archivos base
5. **Fácil mantenimiento** - Cambios globales en un solo lugar
6. **Compilación optimizada** - CSS comprimido para producción 