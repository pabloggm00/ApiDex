# ApiDex

Esta aplicación consiste en una gestión de Pokémon relacionado con el juego Pokémon GO.

Se basa en una API REST implementada con Spring Boot y Kotlin.

Un usuario no logueado no podrá hacer nada. Para que un usuario pueda loguearse debe estar registrado previamente. 

El usuario logueado puede:
 - Ver los Pokémon de la Pokédex
 - Agregar un Pokémon a favoritos 
 - Agregar un Pokémon a capturados
 - Ver detalle de un Pokémon
 - Editar ciertos atributos de un Pokémon
 - Duplicar un Pokémon
 - Evolucionar un Pokémon
 - Ver su perfil y gestionar dicho perfil
 - Filtrar Pokémon por tipo y generación

Los Pokémon que están en la base de datos son originales y por tano no podrá ni editar, ni borrar, ni alterar nada del Pokémon. Para ello se implementa la opción de duplicar, ya que los Pokémon duplicados no son originales y por lo tanto se podrán editar.

Las imágenes de las viviendas se guardan en Imgur, un sitio web para alojar imágenes en linea.

Los usuarios por defecto tienen de avatar un robot genérico que se carga de la API Robohash, pero en cualquier momento pueden editar su foto y meter una cualquiera.

La documentación se ha hecho con el propio de Kotlin: Dokka. En la carpeta razíz está la carpeta con toda la documentación.

Se ha implementado también una documentación exclusiva para Controllers a través de Swagger.

La aplicación tiene también una vista en Android, donde se conecta con la API.

## Información para ejecutar la aplicación (SOLO API)

Hay que abrir el proyecto con Intellij IDEA, hacer doble clic en la tecla CTRL y escribir el comando: gradle bootRun, así sera ejecutado.

Se proporciona un archivo JSON con la colección de POSTMAN con todas las rutas de la API REST con datos de ejemplo en el Body.

La aplicación por defecto se ejecuta en el puerto 9000

## Información para ejecutar la aplicación (Android y API)

Hay que tener la API ejecutándose, por lo que se deberá realizar los pasos anteriores.

Una vez ejecutada la API y el proyecto abierto en Android Studio, le daremos al boton del play arriba y empezará a ejecutarse en un emulador.



