package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Mensaje;

public interface RecibirMailService {
    void sendSimpleMessage(Mensaje mensaje);
}