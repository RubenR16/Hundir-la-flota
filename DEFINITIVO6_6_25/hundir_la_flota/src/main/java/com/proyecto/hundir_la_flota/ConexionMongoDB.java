package com.proyecto.hundir_la_flota;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConexionMongoDB {
    private static final String URI = "mongodb+srv://root:alumnoalumno@hundirlaflota.vayfcww.mongodb.net/?authSource=admin";
    private static final String DATABASE_NAME = "hundir_la_flota";
    private static final String COLLECTION_NAME = "partidas_eventos";
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    
    public ConexionMongoDB() {
        try {
            mongoClient = MongoClients.create(URI);
            database = mongoClient.getDatabase(DATABASE_NAME);
            collection = database.getCollection(COLLECTION_NAME);
            System.out.println("Conexión a MongoDB establecida correctamente.");
        } catch (Exception e) {
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Crear un nuevo documento de partida
    public void crearDocumentoPartida(Long idPartida, String usuario1, String usuario2) {
        try {
            Document partidaDoc = new Document("id_partida", idPartida.toString())
                    .append("usuario_1", usuario1)
                    .append("usuario_2", usuario2)
                    .append("fecha_inicio", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .append("eventos", new ArrayList<Document>());
            
            collection.insertOne(partidaDoc);
            System.out.println("Documento de partida creado en MongoDB con ID: " + idPartida);
        } catch (Exception e) {
            System.err.println("Error al crear documento de partida: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Agregar evento de chat
    public void agregarEventoChat(Long idPartida, String jugador, String mensaje) {
        try {
            Document eventoChat = new Document("tipo", "chat")
                    .append("hora", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .append("jugador", jugador)
                    .append("mensaje", mensaje);
            
            Document filtro = new Document("id_partida", idPartida.toString());
            Document actualizacion = new Document("$push", new Document("eventos", eventoChat));
            
            collection.updateOne(filtro, actualizacion);
            System.out.println("Evento de chat agregado para partida " + idPartida);
        } catch (Exception e) {
            System.err.println("Error al agregar evento de chat: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Agregar evento de movimiento
    public void agregarEventoMovimiento(Long idPartida, String jugador, String casilla, String resultado) {
        try {
            Document eventoMovimiento = new Document("tipo", "movimiento")
                    .append("hora", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .append("jugador", jugador)
                    .append("casilla", casilla)
                    .append("resultado", resultado);
            
            Document filtro = new Document("id_partida", idPartida.toString());
            Document actualizacion = new Document("$push", new Document("eventos", eventoMovimiento));
            
            collection.updateOne(filtro, actualizacion);
            System.out.println("Evento de movimiento agregado para partida " + idPartida);
        } catch (Exception e) {
            System.err.println("Error al agregar evento de movimiento: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Agregar evento de barco hundido
    public void agregarEventoBarcoHundido(Long idPartida, String jugador, String casilla, int tamañoBarco) {
        try {
            Document eventoHundido = new Document("tipo", "barco_hundido")
                    .append("hora", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .append("jugador", jugador)
                    .append("casilla", casilla)
                    .append("tamaño_barco", tamañoBarco);
            
            Document filtro = new Document("id_partida", idPartida.toString());
            Document actualizacion = new Document("$push", new Document("eventos", eventoHundido));
            
            collection.updateOne(filtro, actualizacion);
            System.out.println("Evento de barco hundido agregado para partida " + idPartida);
        } catch (Exception e) {
            System.err.println("Error al agregar evento de barco hundido: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Agregar evento de fin de partida
    public void agregarEventoFinPartida(Long idPartida, String ganador, String razon) {
        try {
            Document eventoFin = new Document("tipo", "fin_partida")
                    .append("hora", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .append("ganador", ganador)
                    .append("razon", razon); // "victoria", "abandono", etc.
            
            Document filtro = new Document("id_partida", idPartida.toString());
            Document actualizacion = new Document("$push", new Document("eventos", eventoFin));
            
            collection.updateOne(filtro, actualizacion);
            System.out.println("Evento de fin de partida agregado para partida " + idPartida);
        } catch (Exception e) {
            System.err.println("Error al agregar evento de fin de partida: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Obtener todos los eventos de una partida
    public Document obtenerEventosPartida(Long idPartida) {
        try {
            Document filtro = new Document("id_partida", idPartida.toString());
            return collection.find(filtro).first();
        } catch (Exception e) {
            System.err.println("Error al obtener eventos de partida: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    // Cerrar conexión
    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
    
    // Método de prueba
    public static void main(String[] args) {
        ConexionMongoDB mongo = new ConexionMongoDB();
        
        try {
            // Crear documento de partida de prueba
            mongo.crearDocumentoPartida(999L, "TestUser1", "TestUser2");
            
            // Agregar algunos eventos de prueba
            mongo.agregarEventoChat(999L, "TestUser1", "¡Hola! ¿Listo para jugar?");
            mongo.agregarEventoChat(999L, "TestUser2", "¡Sí! ¡Buena suerte!");
            mongo.agregarEventoMovimiento(999L, "TestUser1", "A5", "agua");
            mongo.agregarEventoMovimiento(999L, "TestUser2", "B3", "acierto");
            mongo.agregarEventoBarcoHundido(999L, "TestUser2", "B3", 2);
            mongo.agregarEventoFinPartida(999L, "TestUser2", "victoria");
            
            // Leer los eventos
            Document partida = mongo.obtenerEventosPartida(999L);
            if (partida != null) {
                System.out.println("Documento de partida:");
                System.out.println(partida.toJson());
            }
            
        } finally {
            mongo.cerrarConexion();
        }
    }
}