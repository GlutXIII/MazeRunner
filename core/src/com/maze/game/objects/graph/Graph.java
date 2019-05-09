package com.maze.game.objects.graph;

import java.util.ArrayList;
import java.util.List;

// GRAFY NIESKIEROWANE
public class Graph {
    // liczba krawedzi
    private int e;
    // liczba wierzcholkow
    private int v;
    // tablica list sasiedztwa danego wierzcholka
    private List<Integer>[] adjacencyList;

    /**
     *
     * @param v
     *            ilosc wierzcholkow w grafie
     */
    @SuppressWarnings("unchecked")
    public Graph(int v) {
        this.v = v;
        this.e = 0;
        adjacencyList = (List<Integer>[]) new List[v];
        for (int i = 0; i < v; i++) {
            adjacencyList[i] = new ArrayList<Integer>();
        }
    }

    /**
     * Dodaje krawedz v-w do grafu nieskierowanego.
     *
     * @param v
     *            jeden z wierzcholkow krawedzi
     * @param w
     *            drugi z wierzcholkow krawedzi
     */
    public void addEdge(int v, int w) {
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
        e++;
    }

    /**
     *
     * @return liczbe krawedzi
     */
    public int getNumberOfEdges() {
        return e;
    }

    /**
     * @return liczbe wierzcholkow
     */
    public int getNumberOfVertices() {
        return v;
    }

    /**
     * Zwraca liste sasiedztwa danego wierzcholka.
     *
     * @param v
     *            indeks wierzcholka skierowanego
     * @return zwraca iterowalna kolekcje wierzcholkow sasiadujacych
     */
    public Iterable<Integer> getAdjacencyList(int v) {
        return adjacencyList[v];
    }

    /**
     * @return opis grafu.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        s.append("wierzcholki: ").append(v).append("; krawedzie: ").append(e)
                .append(newLine);
        for (int i = 0; i < v; i++) {
            s.append(i).append(": ");
            for (int w : adjacencyList[i]) {
                s.append(w).append(" ");
            }
            s.append(newLine);
        }
        return s.toString();
    }
}

