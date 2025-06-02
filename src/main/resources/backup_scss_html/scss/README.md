# Mixins para Animaciones

Este archivo contiene mixins SASS para crear animaciones de bordes iluminados.

## Uso Simple con CSS

Si no quieres usar SASS, puedes simplemente incluir el archivo CSS y usar las clases predefinidas:

```html
<!-- Incluir el CSS -->
<link rel="stylesheet" href="/css/game.css">

<!-- Usar las clases -->
<div class="animated-card">Tarjeta con bordes animados azules</div>
<div class="animated-card-red">Tarjeta con bordes animados rojos</div>
<div class="animated-card-green">Tarjeta con bordes animados verdes</div>
<div class="animated-card-boss">Tarjeta con efectos intensos</div>
```

## Uso con SASS

Si quieres aprovechar los mixins, puedes importar el archivo de animaciones:

```scss
@import 'animations';

.mi-elemento {
    @include animated-card(#ff9900, #ffcc00, 3s);
}

.mi-elemento-con-borde-flow {
    @include border-flow(#ff9900, 2s);
}
```

## Mixins Disponibles

### animated-card
```scss
@include animated-card($colorPrimario, $colorSecundario, $tiempoAnimacion: 4s);
```

- `$colorPrimario`: Color principal para la animación
- `$colorSecundario`: Color secundario para la animación  
- `$tiempoAnimacion`: Duración de la animación (defecto: 4s)

### border-flow
```scss
@include border-flow($color: #4499ff, $duration: 1.5s);
```

- `$color`: Color del borde con flujo
- `$duration`: Duración de la animación (defecto: 1.5s) 