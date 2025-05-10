# Sistema de Temas SASS para Breakout

Este proyecto utiliza SASS para gestionar estilos y crear temas personalizables para el juego Breakout. 

## Estructura de Archivos

```
css/
│
├── scss/               # Archivos base SCSS
│   ├── _variables.scss # Variables globales 
│   ├── _mixins.scss    # Mixins reutilizables
│   ├── game.scss       # Estilos específicos para el juego
│   ├── index.scss      # Estilos para la landing page
│   ├── ranking.scss    # Estilos para la página de ranking
│   └── main.scss       # Archivo principal que importa todos los demás
│
└── temas/              # Temas personalizables
    ├── azul.scss       # Tema azul (por defecto)
    ├── rojo.scss       # Tema rojo
    ├── verde.scss      # Tema verde
    └── jefe.scss       # Tema para niveles jefe
```

## Cómo Usar

### Instalar Dependencias

```bash
npm install
```

### Compilar todos los estilos

```bash
npm run build
```

### Modo desarrollo (compila automáticamente al guardar)

```bash
npm run sass
npm run sass-temas
```

## Crear un Nuevo Tema

1. Crea un nuevo archivo en la carpeta `css/temas/`, por ejemplo `morado.scss`
2. Importa las variables base e implementa tu tema:

```scss
// Tema Morado
@import '../scss/variables';

// Sobrescribir variables principales para crear tema
$color-primario: rgba(128, 0, 128, 1);  // Morado
$color-secundario: rgba(147, 112, 219, 1); // Lavanda
$intensidad-brillo: 1.1;
$tiempo-animacion-principal: 3.5s;

// Importar el resto para compilar el tema completo
@import '../scss/main';
```

3. Compila los temas: `npm run build-temas`

## Uso en HTML

Para usar un tema específico, enlázalo en tu HTML:

```html
<!-- Tema por defecto -->
<link rel="stylesheet" href="/css/game.css">

<!-- O para usar un tema específico -->
<link rel="stylesheet" href="/css/temas-compilados/rojo.css">
```

## Personalizar Animaciones

Puedes crear nuevas variantes de tarjeta animada usando los mixins:

```scss
// En tu archivo de tema o en un archivo personalizado
.mi-tarjeta-personalizada {
    @include animated-card(
        $colorPrimario: rgba(255, 215, 0, 1), // Oro
        $colorSecundario: rgba(218, 165, 32, 1), // Dorado
        $tiempoAnimacion: 3s,
        $intensidad: 1.3
    );
}
``` 