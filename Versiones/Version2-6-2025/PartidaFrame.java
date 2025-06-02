package com.proyecto.hundir_la_flota;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PartidaFrame extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;
    private JTextField textField;
    private JButton btnEnviar;
    private JLabel lblTurno;
    private JLabel lblDisparosRestantes;
    private JLabel lblPuntuacion;
    
    // Sistema de puntuación
    private int puntuacion = 0;
    private static final int PUNTOS_ACIERTO = 10;
    private static final int PUNTOS_HUNDIDO = 100;
    private static final double MULTIPLICADOR_VICTORIA = 1.5;
    
    // Estadísticas para la base de datos
    private int barcosHundidos = 0;
    private int casillasAguaGolpeadas = 0;
    private String nombreUsuario;
    
    // Componentes del juego
    private TableroPanel tableroPropio;
    private TableroEnemigo tableroEnemigo;
    private LogicaPartida logicaJuego;
    
    // Red y comunicación
    private ServidorPartida servidor;
    private ClientePartida cliente;
    private boolean esServidor;
    private boolean miTurno;
    
    // Control de disparos
    private static final int DISPAROS_POR_TURNO = 3;
    private int disparosRestantes;
    
    // Hilo para escuchar mensajes
    private Thread hiloEscucha;
    private boolean partidaActiva = true;
    private int ultimoDisparoFila = -1;
    private int ultimoDisparoColumna = -1;

    public PartidaFrame(List<Barco> barcosColocados, boolean esServidor, 
                       ServidorPartida servidor, ClientePartida cliente, String nombreUsuario) {
        this.esServidor = esServidor;
        this.servidor = servidor;
        this.cliente = cliente;
        this.miTurno = esServidor; // El servidor empieza primero
        this.disparosRestantes = miTurno ? DISPAROS_POR_TURNO : 0;
        this.nombreUsuario = nombreUsuario;
        
        // Inicializar lógica del juego
        logicaJuego = new LogicaPartida(barcosColocados);
        
        inicializarInterfaz();
        mostrarBarcosEnTablero(barcosColocados);
        inicializarComunicacion();
        
        // Mostrar mensaje inicial
        agregarMensaje(esServidor ? "Eres el servidor. Tu turno para disparar. Tienes " + DISPAROS_POR_TURNO + " disparos." : 
                                  "Eres el cliente. Espera tu turno.");
    }

    private void inicializarInterfaz() {
        setTitle("Partida - Hundir la Flota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        
        // Panel principal con los dos tableros
        JPanel panelTableros = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Panel izquierdo - Mi tablero
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Tu Campo"));
        tableroPropio = new TableroPanel();
        tableroPropio.deshabilitarColocacion(); // Deshabilitar colocación durante partida
        panelIzquierdo.add(tableroPropio, BorderLayout.CENTER);
        
        // Panel derecho - Tablero enemigo
        JPanel panelDerecho = new JPanel(new BorderLayout());
        JPanel panelInfoEnemigo = new JPanel(new GridLayout(3, 1));
        lblTurno = new JLabel(miTurno ? "Campo Enemigo - TU TURNO" : "Campo Enemigo - ESPERA", JLabel.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Etiqueta para mostrar disparos restantes
        lblDisparosRestantes = new JLabel("Disparos restantes: " + disparosRestantes, JLabel.CENTER);
        lblDisparosRestantes.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Etiqueta para mostrar puntuación
        lblPuntuacion = new JLabel("Puntuación: " + puntuacion, JLabel.CENTER);
        lblPuntuacion.setFont(new Font("Arial", Font.BOLD, 12));
        lblPuntuacion.setForeground(new Color(0, 100, 0)); // Verde oscuro
        
        panelInfoEnemigo.add(lblTurno);
        panelInfoEnemigo.add(lblDisparosRestantes);
        panelInfoEnemigo.add(lblPuntuacion);
        
        panelDerecho.add(panelInfoEnemigo, BorderLayout.NORTH);
        tableroEnemigo = new TableroEnemigo(this);
        panelDerecho.add(tableroEnemigo, BorderLayout.CENTER);
        
        panelTableros.add(panelIzquierdo);
        panelTableros.add(panelDerecho);
        
        // Panel inferior para chat
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createTitledBorder("Chat"));
        
        // Área de mensajes
        textArea = new JTextArea(8, 50);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        // Panel para escribir mensajes
        JPanel panelChat = new JPanel(new BorderLayout());
        textField = new JTextField();
        textField.addActionListener(e -> enviarMensajeChat());
        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(e -> enviarMensajeChat());
        
        panelChat.add(textField, BorderLayout.CENTER);
        panelChat.add(btnEnviar, BorderLayout.EAST);
        
        panelInferior.add(scrollPane, BorderLayout.CENTER);
        panelInferior.add(panelChat, BorderLayout.SOUTH);
        
        contentPane.add(panelTableros, BorderLayout.CENTER);
        contentPane.add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void inicializarComunicacion() {
        hiloEscucha = new Thread(() -> {
            try {
                String mensaje;
                while (partidaActiva && (mensaje = (esServidor ? servidor.getIn().readLine() : 
                                                              cliente.getIn().readLine())) != null) {
                    final String mensajeFinal = mensaje;
                    SwingUtilities.invokeLater(() -> procesarMensaje(mensajeFinal));
                }
            } catch (IOException e) {
                if (partidaActiva) {
                    SwingUtilities.invokeLater(() -> 
                        agregarMensaje("Error de conexión: " + e.getMessage()));
                }
            }
        });
        hiloEscucha.start();
    }
    
    // Método llamado desde TableroEnemigo cuando se hace clic
    public void realizarDisparo(int fila, int columna) {
        if (!miTurno || disparosRestantes <= 0) {
            if (!miTurno) {
                agregarMensaje("¡No es tu turno!");
            } else {
                agregarMensaje("¡Ya has usado todos tus disparos!");
            }
            return;
        }
        
        // Guardar coordenadas del último disparo
        ultimoDisparoFila = fila;
        ultimoDisparoColumna = columna;
        
        // Enviar disparo al oponente
        String mensaje = "DISPARO:" + fila + "," + columna;
        if (esServidor) {
            servidor.getOut().println(mensaje);
        } else {
            cliente.getOut().println(mensaje);
        }
        
        agregarMensaje("Disparaste a " + (char)('A' + fila) + (columna + 1));
        
        // Decrementar disparos restantes
        disparosRestantes--;
        actualizarEtiquetaDisparos();
        
        // Si ya no quedan disparos, cambiar el turno
        if (disparosRestantes <= 0) {
            miTurno = false;
            actualizarEtiquetaTurno();
        }
    }
    
    private void procesarMensaje(String mensaje) {
        if (mensaje.startsWith("DISPARO:")) {
            procesarDisparoRecibido(mensaje);
        } else if (mensaje.startsWith("RESULTADO:")) {
            procesarResultadoDisparo(mensaje);
        } else if (mensaje.startsWith("FIN_TURNO")) {
            procesarFinTurno();
        } else if (mensaje.startsWith("CHAT:")) {
            agregarMensaje("Oponente: " + mensaje.substring(5));
        } else if (mensaje.equals("DERROTA")) {
            finalizarPartida(true); // El oponente perdió, nosotros ganamos
        }
    }
    
    private void procesarDisparoRecibido(String mensaje) {
        // Formato: "DISPARO:fila,columna"
        String[] partes = mensaje.substring(8).split(",");
        int fila = Integer.parseInt(partes[0]);
        int columna = Integer.parseInt(partes[1]);
        
        boolean acierto = logicaJuego.recibirDisparo(fila, columna);
        
        // Actualizar nuestro tablero visual
        tableroPropio.marcarDisparoRecibido(fila, columna, acierto);
        
        // Verificar si se hundió un barco
        String resultado = "RESULTADO:" + (acierto ? "ACIERTO" : "AGUA");
        if (acierto) {
            Barco barcoTocado = logicaJuego.obtenerBarcoEn(fila, columna);
            if (barcoTocado != null && logicaJuego.verificarBarcoHundido(barcoTocado)) {
                resultado = "RESULTADO:HUNDIDO:" + barcoTocado.filaInicial + "," + 
                           barcoTocado.columnaInicial + "," + barcoTocado.tamaño + "," + barcoTocado.horizontal;
                agregarMensaje("¡Tu barco de tamaño " + barcoTocado.tamaño + " fue hundido!");
            } else {
                agregarMensaje("Te dieron en " + (char)('A' + fila) + (columna + 1));
            }
        } else {
            agregarMensaje("El enemigo falló en " + (char)('A' + fila) + (columna + 1));
        }
        
        // Enviar resultado
        if (esServidor) {
            servidor.getOut().println(resultado);
        } else {
            cliente.getOut().println(resultado);
        }
        
        // Verificar fin del juego
        if (logicaJuego.todosLosBarcosHundidos()) {
            if (esServidor) {
                servidor.getOut().println("DERROTA"); // El servidor perdió, envía DERROTA al cliente
            } else {
                cliente.getOut().println("DERROTA"); // El cliente perdió, envía DERROTA al servidor
            }
            finalizarPartida(false); // Nosotros perdimos
        }
    }
    
    private void procesarResultadoDisparo(String mensaje) {
        // Formato: "RESULTADO:ACIERTO/AGUA/HUNDIDO:datos_barco"
        String[] partes = mensaje.substring(10).split(":");
        String resultado = partes[0];
        
        // Marcar el resultado en el tablero enemigo
        if (ultimoDisparoFila >= 0 && ultimoDisparoColumna >= 0) {
            if (resultado.equals("ACIERTO")) {
                tableroEnemigo.marcarDisparo(ultimoDisparoFila, ultimoDisparoColumna, true);
                agregarMensaje("¡Acierto! +10 puntos");
                
                // Sumar puntos por acierto
                sumarPuntos(PUNTOS_ACIERTO);
            } else if (resultado.equals("HUNDIDO")) {
                tableroEnemigo.marcarDisparo(ultimoDisparoFila, ultimoDisparoColumna, true);
                
                // Marcar todo el barco como hundido si tenemos los datos
                if (partes.length > 1) {
                    String[] datosBarco = partes[1].split(",");
                    int filaBarco = Integer.parseInt(datosBarco[0]);
                    int columnaBarco = Integer.parseInt(datosBarco[1]);
                    int tamañoBarco = Integer.parseInt(datosBarco[2]);
                    boolean horizontalBarco = Boolean.parseBoolean(datosBarco[3]);
                    
                    tableroEnemigo.marcarBarcoHundido(filaBarco, columnaBarco, tamañoBarco, horizontalBarco);
                    
                    // Sumar puntos por acierto + puntos por hundir barco
                    sumarPuntos(PUNTOS_ACIERTO + PUNTOS_HUNDIDO);
                    barcosHundidos++; // Incrementar contador de barcos hundidos
                    agregarMensaje("¡Barco hundido! +" + (PUNTOS_ACIERTO + PUNTOS_HUNDIDO) + " puntos");
                }
                
                // Verificar si hemos hundido todos los barcos enemigos
                if (todosLosBarcosEnemigosHundidos()) {
                    agregarMensaje("¡Has hundido todos los barcos enemigos!");
                    finalizarPartida(true); // Nosotros ganamos
                    return;
                }
            } else {
                tableroEnemigo.marcarDisparo(ultimoDisparoFila, ultimoDisparoColumna, false);
                casillasAguaGolpeadas++; // Incrementar contador de agua
                agregarMensaje("Agua.");
            }
        }
        
        // Si ya no quedan disparos, enviar mensaje de fin de turno
        if (disparosRestantes <= 0) {
            if (esServidor) {
                servidor.getOut().println("FIN_TURNO");
            } else {
                cliente.getOut().println("FIN_TURNO");
            }
        }
    }
    
    private void procesarFinTurno() {
        // Es nuestro turno después de que el oponente termine sus disparos
        miTurno = true;
        disparosRestantes = DISPAROS_POR_TURNO;
        actualizarEtiquetaTurno();
        actualizarEtiquetaDisparos();
        agregarMensaje("¡Tu turno! Tienes " + DISPAROS_POR_TURNO + " disparos.");
    }
    
    private void enviarMensajeChat() {
        String texto = textField.getText().trim();
        if (!texto.isEmpty()) {
            if (esServidor) {
                servidor.getOut().println("CHAT:" + texto);
            } else {
                cliente.getOut().println("CHAT:" + texto);
            }
            agregarMensaje("Tú: " + texto);
            textField.setText("");
        }
    }
    
    private void agregarMensaje(String mensaje) {
        textArea.append(mensaje + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    private void actualizarEtiquetaTurno() {
        lblTurno.setText(miTurno ? "Campo Enemigo - TU TURNO" : "Campo Enemigo - ESPERA");
    }
    
    private void actualizarEtiquetaDisparos() {
        lblDisparosRestantes.setText("Disparos restantes: " + disparosRestantes);
        if (miTurno && disparosRestantes > 0) {
            lblDisparosRestantes.setForeground(Color.BLUE);
        } else {
            lblDisparosRestantes.setForeground(Color.GRAY);
        }
    }
    
    private void actualizarEtiquetaPuntuacion() {
        lblPuntuacion.setText("Puntuación: " + puntuacion);
        // Efecto visual para destacar cambios en la puntuación
        lblPuntuacion.setForeground(new Color(0, 150, 0));
        Timer timer = new Timer(1000, e -> lblPuntuacion.setForeground(new Color(0, 100, 0)));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void sumarPuntos(int puntos) {
        puntuacion += puntos;
        actualizarEtiquetaPuntuacion();
    }
    
    private void finalizarPartida(boolean victoria) {
        partidaActiva = false;
        
        // Si ganó, aplicar multiplicador a la puntuación
        if (victoria) {
            int puntuacionFinal = (int)(puntuacion * MULTIPLICADOR_VICTORIA);
            int bonificacion = puntuacionFinal - puntuacion;
            
            agregarMensaje("¡Victoria! Bonificación de puntos: +" + bonificacion);
            agregarMensaje("Puntuación final: " + puntuacionFinal);
            
            puntuacion = puntuacionFinal;
            actualizarEtiquetaPuntuacion();
        }
        
        // Actualizar estadísticas en la base de datos
        actualizarEstadisticasBaseDatos();
        
        String mensaje = victoria ? 
                "¡Has ganado la partida!\nPuntuación final: " + puntuacion : 
                "Has perdido la partida.\nPuntuación final: " + puntuacion;
        
        JOptionPane.showMessageDialog(this, mensaje, "Fin de partida", 
                                    victoria ? JOptionPane.INFORMATION_MESSAGE : 
                                             JOptionPane.WARNING_MESSAGE);
    }
    
    private void actualizarEstadisticasBaseDatos() {
        try {
            ConexionMySQL conexion = new ConexionMySQL();
            Connection con = conexion.getCon();
            
            String sql = "UPDATE usuarios SET " +
                        "partidas_jugadas = partidas_jugadas + 1, " +
                        "barcos_hundidos = barcos_hundidos + ?, " +
                        "puntos_totales = puntos_totales + ?, " +
                        "casillas_agua_golpeadas = casillas_agua_golpeadas + ? " +
                        "WHERE nombre_usuario = ?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, barcosHundidos);
            stmt.setInt(2, puntuacion);
            stmt.setInt(3, casillasAguaGolpeadas);
            stmt.setString(4, nombreUsuario);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                agregarMensaje("Estadísticas guardadas en la base de datos.");
            } else {
                agregarMensaje("Error: No se pudieron guardar las estadísticas.");
            }
            
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            agregarMensaje("Error al guardar estadísticas: " + e.getMessage());
        }
    }
    
    public boolean esMiTurno() {
        return miTurno && disparosRestantes > 0;
    }
    
    // Método para mostrar barcos en el tablero propio al inicio de la partida
    private void mostrarBarcosEnTablero(List<Barco> barcos) {
        Color[] coloresBarcos = {
            new Color(235, 152, 78),  // Barco de 5
            new Color(100, 255, 100), // Barco de 4  
            new Color(100, 100, 255), // Barco de 3 (1)
            new Color(255, 255, 100), // Barco de 3 (2)
            new Color(255, 150, 255)  // Barco de 2
        };
        
        for (int i = 0; i < barcos.size(); i++) {
            Barco barco = barcos.get(i);
            Color colorBarco = coloresBarcos[i];
            
            for (int j = 0; j < barco.tamaño; j++) {
                int fila = barco.filaInicial + (barco.horizontal ? 0 : j);
                int columna = barco.columnaInicial + (barco.horizontal ? j : 0);
                
                if (fila >= 0 && fila < 10 && columna >= 0 && columna < 10) {
                    tableroPropio.mostrarBarcoEnCasilla(fila, columna, colorBarco);
                }
            }
        }
    }

    // Método para verificar si todos los barcos enemigos han sido hundidos
    private boolean todosLosBarcosEnemigosHundidos() {
        // Contar casillas marcadas como hundidas en el tablero enemigo
        int casillasHundidas = 0;
        int totalCasillasBarcos = 5 + 4 + 3 + 3 + 2; // Total de casillas de barcos (17)
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Verificar si la casilla está marcada como hundida o como acierto
                // Esto es una aproximación, idealmente necesitaríamos más información del tablero enemigo
            }
        }
        
        // Por simplicidad, podemos usar un contador de barcos hundidos
        // En una implementación más robusta, el servidor debería enviar esta información
        return false; // Por ahora retornamos false, se puede mejorar con más lógica
    }
    
    @Override
    public void dispose() {
        partidaActiva = false;
        if (hiloEscucha != null) {
            hiloEscucha.interrupt();
        }
        try {
            if (esServidor && servidor != null) {
                servidor.cerrar();
            } else if (cliente != null) {
                cliente.cerrar();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.dispose();
    }
}
