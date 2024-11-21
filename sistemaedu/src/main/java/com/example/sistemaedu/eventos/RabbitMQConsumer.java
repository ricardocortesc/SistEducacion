package com.example.sistemaedu.eventos;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "course.assignment.events")
    public void handleAssignmentEvent(AssignmentEvent event) {
        // Mostrar el evento recibido
        System.out.println("Evento recibido: " + event);

        // Generar el mensaje de notificación
        String notificationMessage = String.format("Notificacion: El estudiante %s ha sido asignado al curso %s.",
                event.getStudentName(), event.getCourseName());

        // Lógica: Mostrar detalles del evento y la notificación
        System.out.println("Estudiante asignado: " + event.getStudentName());
        System.out.println("Curso asignado: " + event.getCourseName());
        System.out.println("Profesor titular: " + event.getProfessorName());
        System.out.println("Mensaje de notificacion: " + notificationMessage);

        // Aquí puedes agregar más lógica si deseas procesar la notificación de alguna otra forma
    }
}
