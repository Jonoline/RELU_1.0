# RELU
Aplicación destinada a estudiante, docentes y funcionarios de la Universidad de la Frontera. La cual consiste en una aplicación que modificará el sistema actual en la gestión de reserva de Logias de la Universidad de la Frontera..

Dentro de la rama Develop se encuentra el código para el avance 1, este código se debe inicializar desde menu.java.


Algunos Commits interesantes ( mayormente se trabajo desde el pc de Cristobal lo cual explica la diferencia en cantidad de commits)  
Feature/Menu  
Initial commit Jonathan  
añadi jUnit a las dependencias Cristobal  
Menu Inicial Jonathan  
se añade clase de Validacion + metodos incompletos Cristobal  
creacion clase reservasLogias Cristobal  
creacion metodos llenarhoras y llenar dias para generar matriz de logia Cristobal  
terminado la creación inicial de la matriz Cristobal  
nuevos metodos aun por mejorar para seleccionar dia y hora Cristobal  
Esqueleto Menu Armado Primera Opcion Jonathan  
Mejore el "Validacion.java", incluyendo que el main llame a las funciones, añadi nuevos metodos y defini que los validadores sean booleanos Joaquin  
Deje funcionando todo el programa, antes al correr menu, no estaba "corriendo" el Validacion.java, y ahora si esta funcionando. Tambien note un error que es que despues de validar, se muestra la matriz y despues se mostraba el menu de opciones, y era error de orden de codigo, ahora esta todo funcionando, los 3 archivos. Joaquin  
actualizacion del metodo validarMatricula, se le añade validacion por matricula existente en base de datos (actualmente un arraylist) Cristobal  

Develop  
Se añadio el metodo mostrarMatriz que muestra cada celda relevante con "disponible" o "reservado". tambien se cambio la linea if(horas[dia][hora] == matricula) por if (horas[dia][hora].equals(matricula)) ya que la anterior causaba problemas con el metodo eliminarReserva Esteban  
se añadio el metodo eliminarReserva que cambia el numero del espacio de la matriz por un 1 junto a su respectiva validacion Esteban  
actualizacion del metodo validarMatricula, se le añade validacion por patron de matricula y por matricula existente en base de datos (actualmente un arraylist) Cristobal  
Cambio el metodo leermatricula a publico y se hace un llamado en validar hora. se hizo cambio en el orden de como se muestran dias y horas en metodo seleccionardiahora Esteban  
2 archivos de test con pruebas de validarmatricula , obtenerdia y obtenerhora Cristobal  

