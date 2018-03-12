# Proyecto Organizacion de computadoras

## Programa de simulación de la eficacia del uso de la memoria Caché

### Elaborar un programa que simule el uso de la memoria caché con las siguientes características:
* El tamaño de la memoria RAM será de 4096 bytes
* La memoria caché de 64 bytes
* El tamaño del bloque de 8  bytes
* El tamaño del conjunto es de 4 líneas (solo aplica cuando el caché es asociativo por conjunto) 
* La memoria RAM (el cual es un arreglo) se llenará con números entre 0 y 255 leídos del archivos datos.txt
* Se creará un programa para ordenar números en forma ascendente
* Se crearán 2  funciones , la función “leer” y la función “escribir” con los parámetros; i:  posición donde se desea leer o escribir (entre 0 y 4095)
* Los Tiempos de acceso a la memoria son los siguientes (a). 0.1 µs si se lee o escribe de la RAM (no hay caché), (b). 0.01 µs si se lee o escribe de la caché, (c). 0.11 µs si se lee de la caché pero antes hay que pasarlo de la RAM a la cache, (d). 0.22 µs Si se lee de la caché, pero antes hay que pasarlo de la RAM a la caché y luego de la caché a la RAM
* El programa debe correrse una vez para cada uno de los 4 tipos de caché, el arreglo de los números a ordenar debe contener los mismos datos desordenados en cada corrida se debe mostrar el siguiente reporte (aquí aparecen datos inventados)
* Las funciones “leer” y “escribir” dependiendo del tipo de caché deben determinar cuánto tiempo le tomó efectuar la operación, por ejemplo si no se utiliza caché, el tiempo es de 0.1 µs, si utiliza caché directo y determina que el dato ya está en el caché, el tiempo será de 0.01 µs, pero si no está en la caché el tiempo será de 0.11 µs, o peor aún si está en el caché pero  la línea se ha modificado, el tiempo será de 0.22 µs 
* Recordar que los valores de las etiquetas no forman parte de los 64 bytes de la caché
* Recordar que cada línea del caché debe tener un bit para indicar si la línea es válida y otro bit para indicar si la línea ha sido modificada.
* Para el caché asociativo las líneas se irán usando en forma secuencial, por lo que debe  haber una variable que indique cual es la siguiente línea a ser utilizada
* Para el caché asociativo por conjuntos también las líneas de cada conjunto se irán utilizando en forma secuencial, o sea debe haber una variable para cada conjunto (o sea un arreglo) que indique cual es la siguiente línea a ser utilizada.
* Pueden hacer grupos de máximo 4 personas
* Deben enviar por medio de la plataforma lo siguiente (a). El programa fuente, (b). El programa ejecutable, (c). Una imagen (print screen) con la salida del programa

## Informe de Proyecto
* [Informe](https://docs.google.com/document/d/1B3PaBSFkYSdtWjk5ftL4Imb2FI_oIRxvEKAWYX47EZ8/edit?usp=sharing)