package com.proyecto.hundir_la_flota;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConexionMongoDB {

    public static void main(String[] args) {
        String uri = "mongodb+srv://root:alumnoalumno@hundirlaflota.vayfcww.mongodb.net/?authSource=admin";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("chat");
            MongoCollection<Document> collection = database.getCollection("registro");

            // Insertar un documento de prueba
            Document doc = new Document("nombre", "Prueba")
                                .append("valor", 123);
            collection.insertOne(doc);
            System.out.println("Documento insertado correctamente.");

            // Leer los documentos
            for (Document d : collection.find()) {
                System.out.println(d.toJson());
            }
        } catch (Exception e) {
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
