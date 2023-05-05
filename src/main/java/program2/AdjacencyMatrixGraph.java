package program2;

import java.util.Arrays;
import java.util.Scanner;

public class AdjacencyMatrixGraph {
    private int[][] matrix;
    private int numNodes;

    public AdjacencyMatrixGraph(int numNodes) {
        this.numNodes = numNodes;
        this.matrix = new int[numNodes][numNodes];

//        for (int i = 0; i < numNodes; i++) {
//            matrix[i][i] = 0;
//        }
    }

    /* Añade un nuevo nodo a la matriz */
    public void addNode() {
        numNodes++;
        this.matrix = copyArray(matrix, numNodes - 1);
    }

    /* Conecta el nodo 1 con el nodo 2 y añade una nueva arista */
    public void addEdge(int node1, int node2) {
        matrix[node1][node2] = 1;
        matrix[node2][node1] = 1;
    }

    /* Elimina un nodo */
    public void removeNode(int node) {
        int[][] newMatrix = new int[numNodes - 1][numNodes - 1];
        int nodeIndex = 0;
        for (int i = 0; i < numNodes; i++) {
            if (i != node) {
                newMatrix[nodeIndex][nodeIndex] = matrix[i][i];
                nodeIndex++;
            }
        }
        numNodes--;
        this.matrix = newMatrix;
    }

    /* Elimina la conexión entre el nodo 1 y el nodo 2 */
    public void removeEdge(int node1, int node2) {
        matrix[node1][node2] = 0;
        matrix[node2][node1] = 0;
    }

    /* Evalua si el nodo 1 y nodo 2 estan conectados */
    public boolean isAdjacent(int node1, int node2) {
        return matrix[node1][node2] == 1 && matrix[node2][node1] == 1;
    }

    /* Imprime la matriz */
    public void printMatrix() {
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int[][] copyArray(int[][] array, int numN) {
        int[][] newMatrix = new int[numNodes][numNodes];
        for (int i = 0; i < numN - 1; i++) {
            newMatrix[i][i] = array[i][i];
        }

        return newMatrix;
    }

    public String getLastNodeName() {
        return String.format("Nodo%s", matrix.length - 1);
    }

    /* Main */
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de nodos: ");
        int numNodes = scanner.nextInt();
        String preventsLineBreak = scanner.nextLine();
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(numNodes);
        boolean programRunning = true;

        while (programRunning) {
            System.out.println("¿Que quieres hacer?");
            System.out.println("a) Añadir nodo.");
            System.out.println("b) Añadir conexión.");
            System.out.println("c) Eliminar nodo.");
            System.out.println("d) Eliminar conexión.");
            System.out.println("e) Verificar sin dos nodos son adyacentes.");
            System.out.println("f) Imprimir.");
            System.out.println("g) Salir.");
            String input = scanner.nextLine();

            if (!input.isEmpty()) {
                switch (input) {
                    case "a":
                        graph.addNode();
                        System.out.println(String.format("Se añadio: %s", graph.getLastNodeName()));
                        break;
                    case "b":
                        System.out.println("Ingresa los nodos que se conectaran");
                        System.out.println("1er nodo:");
                        int node1 = scanner.nextInt();
                        System.out.println("2do nodo:");
                        int node2 = scanner.nextInt();
                        preventsLineBreak = scanner.nextLine();

                        graph.addEdge(node1, node2);
                        break;
                    case "c":
                        System.out.println("Ingresa el nodo que se eliminara");
                        int node = scanner.nextInt();
                        preventsLineBreak = scanner.nextLine();
                        graph.removeNode(node);
                        break;
                    case "d":
                        System.out.println("Ingresa los nodos que componen la conexión que se eliminara");
                        System.out.println("1er nodo:");
                        int _node1 = scanner.nextInt();
                        System.out.println("2do nodo:");
                        int _node2 = scanner.nextInt();
                        preventsLineBreak = scanner.nextLine();

                        graph.removeEdge(_node1, _node2);
                        break;
                    case "e":
                        System.out.println("Ingresa los nodos a evaluar");
                        System.out.println("1er nodo:");
                        int __node1 = scanner.nextInt();
                        System.out.println("2do nodo:");
                        int __node2 = scanner.nextInt();

                        System.out.println(String.format("%s y %s %s son adyacentes", __node1, __node2, graph.isAdjacent(__node1, __node2) ? "si" : "no"));
                        preventsLineBreak = scanner.nextLine();
                        break;
                    case "f":
                        graph.printMatrix();
                        break;
                    case "g":
                        programRunning = false;
                        scanner.close();
                        break;
                }
            }
        }
    }
}
