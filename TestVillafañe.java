package test;

import pista.Pista;
import pista.PistaSimple;
import pista.PosicionesEntradaVaciaException;
import avion.Avion;
import avion.AvionSimple;
import copControl.Dificultad;
import copControl.Juego;
import copControl.Jugador;
import copControl.Mapa;
import copControl.Nivel;
import copControl.Posicion;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestVillafañe {
	Dificultad dificultad;
	Mapa mapa;
	Nivel nivel;
	Avion avion;
	Jugador jugador;
	LinkedList<Pista> pistas;
	
	@BeforeEach
	public void setUp() throws PosicionesEntradaVaciaException{
		mapa = new Mapa();
		dificultad = new Dificultad(1,1,1);
		nivel = new Nivel(mapa, dificultad);
		jugador = new Jugador("PEPE");
		avion = new AvionSimple(new Posicion(1,1), new Posicion(3,3), mapa);
		pistas = new LinkedList();
	}
	
	// TEST 1
	@Test
	public void testChequearSiHayAvionVolando() {
		nivel.colocarAvionEnAire(avion);
		assertTrue(nivel.tieneAvionesVolando());
	}
	
	// TEST 2
	@Test
	public void testChequearSiHayChoqueDeAviones() {
		Avion avion1 = new AvionSimple(new Posicion(1,1), new Posicion(3,3), mapa);
		Avion avion2 = new AvionSimple(new Posicion(3,3), new Posicion(1,1), mapa);
		nivel.colocarAvionEnAire(avion1);
		nivel.colocarAvionEnAire(avion2);
		nivel.avanzarAvionesEnAire();
		assertTrue(nivel.huboChoque());
	}

	// TEST 3
	@Test
	public void testTraerAvionesEnPosicion() {
		nivel.colocarAvionEnAire(avion);
		Avion avionEnPosicion = nivel.getAvionEnPosicion(new Posicion(3,3));
		assertNotNull(avionEnPosicion);
	}

	// TEST 4
	@Test
	public void testFinalizarNiveles() {
		nivel.colocarAvionEnAire(avion);
		Nivel nivel2 = new Nivel(mapa, dificultad);
		Juego juego = new Juego(jugador, Arrays.asList(nivel, nivel2));
		juego.avanzarNivel();
		juego.avanzarNivel();
		assertFalse(juego.estaJugandose());
	}
	
	// TEST 5
	@Test
	public void testGanarJuego() {
		nivel.colocarAvionEnAire(avion);
		Juego juego = new Juego(jugador, Arrays.asList(nivel));
		juego.avanzarNivel();
		assertTrue(juego.esJuegoGanado());
	}
	
	// TEST 6
	@Test
	public void testJugarYAterrizarAvion() throws PosicionesEntradaVaciaException {
		Posicion posicionPista= new Posicion(1,1);
		PistaSimple pistaSimple = new PistaSimple(posicionPista);
		pistas.add(pistaSimple);
		Mapa mapa1 = new Mapa(pistas);
		Nivel nivel1 = new Nivel(mapa1, dificultad);
		nivel1.colocarAvionEnAire(avion);
		Juego juego = new Juego(jugador, Arrays.asList(nivel1));
		juego.vivir();
		assertTrue(juego.getCantidadAvionesAterrizados() == 1);
	}
	
	// TEST 7
	@Test
	public void testColocarAvionYTraerSuVelocidad() throws PosicionesEntradaVaciaException {
		Posicion posicionPista= new Posicion(1,1);
		PistaSimple pistaSimple = new PistaSimple(posicionPista);
		pistas.add(pistaSimple);
		Mapa mapa1 = new Mapa(pistas);
		Nivel nivel1 = new Nivel(mapa1, new Dificultad(4,0,3));
		Juego juego = new Juego(jugador, Arrays.asList(nivel1));
		juego.vivir();
		assertTrue(juego.getVelocidadActual() == 3);
	}
}
