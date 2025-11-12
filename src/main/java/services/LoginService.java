package services;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
/**
 * autor: Génesis
 * fecha: 11/11/2025
 * descripción:
 * interfaz que define se reciba una peticion http que decuelca un
 * optional string con el nombre del ususario actual en caso de logeo
 *
 */

public interface LoginService {
    /**metodo que se debe implementar
    *optional que puede conyener el username en string
    * o estar vacio si no hay sesion
     * getUsername nombre del metodo
     * httpservletrequest se encarga de recibir la peticion hhtp actual
     * que contiene la informacion de la sesion y del usuario
    */
    Optional<String> getUsername(HttpServletRequest request);
}
