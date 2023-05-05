package program2;

import java.util.Iterator;
import java.util.Scanner;

class GraphNode <V> {
    private List<GraphNode> connections;
    private V value;

    public GraphNode(List<GraphNode> connections, V value) {
        this.connections = connections;
        this.value = value;
    }

    public GraphNode(V value) {
        this.connections = new List<GraphNode>();
        this.value = value;
    }

    /**
     * Agrega una nueva conexión al nodo
     * @param node Nodo con el que se conectara
     */
    public void addConnection(GraphNode node) {
        connections.add(node);
    }

    /**
     * Elimina una conexión
     * @param node Nodo conectado
     * @throws Exception
     */
    public void removeConnection(GraphNode node) throws Exception {
        int position = 0;
        for (GraphNode n : connections) {
            if (n.getValue().equals(node.getValue())) {
                connections.deleteAt(position);
                break;
            }
            position++;
        }
    }

    /**
     * Evalua si existe una conexión entre los nodos
     * @param node Nodo a evaluar
     * @return True si existe una conexión
     */
    public boolean isAdjacent(GraphNode node) {
        boolean adjacent = false;
        for (GraphNode n : connections) {
            if (n.getValue().equals(node.getValue())) {
                adjacent = true;
                break;
            }
        }

        return adjacent;
    }

    public List<GraphNode> getConnections() {
        return connections;
    }

    public V getValue() {
        return value;
    }
}

class Node <E> {
    private E value;
    private Node next;
    private Node prev;

    public Node(E value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}

class List <E> implements  Iterable<E> {
    private Node<E> front;
    private Node<E> back;
    private int size;

    public List() {
        front = null;
        back = null;
        size = 0;
    }

    /**
     * Agrega un nodo al final de la lista
     * @param value Valor del nodo
     */
    public void add(E value) {
        Node newNode = new Node(value);

        if (front == null) {
            front = newNode;
            back = newNode;
        } else {
            back.setNext(newNode);
            back = newNode;
        }

        size++;
    }

    /**
     * Busca un nodo en una posición
     * @param pos Posición
     * @return El nodo en la posición
     * @throws Exception
     */
    public E findPos(int pos) throws Exception {
        /* Validar posición */
        if ((pos >= 0) && (pos < size)) {
            /* Se recorren los nodos hasta el nodo de la posición deseada */
            Node<E> temp = front;
            int nodesPos = 0;

            while (nodesPos < pos) {
                temp = temp.getNext();
                nodesPos++;
            }

            return temp.getValue();
        } else {
            throw new Exception(String.format("La posición ingresada no es una posición valida !!"));
        }
    }

    /**
     * Vacia la lista
     */
    public void empty() {
        front = null;
        back = null;
        size = 0;
    }

    /**
     * Elimina un nodo en una posición
     * @param pos Posición
     * @throws Exception
     */
    public void deleteAt(int pos) throws Exception {
        int nodeCount = size;

        /* Validar posición */
        if ((pos >= 0) && (pos < nodeCount)) {

            if (nodeCount == 1) {
                empty();
            } else if (pos == 0) {
                front = front.getNext();
                size--;
            } else {
                /* Se recorren los nodos hasta el nodo anterior a la posición deseada */
                Node temp = front;
                int nodesPos = 1;

                while (nodesPos < pos) {
                    temp = temp.getNext();
                    nodesPos++;
                }

                temp.setNext(temp.getNext().getNext());

                if (temp.getNext() == null) {
                    back = temp;
                }

                size--;
            }
        } else {
            throw new Exception(String.format("La posición ingresada no es una posición valida !!"));
        }
    }

    /**
     * Itera la lista
     * @return Iterator
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            public int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                E element = null;
                try {
                    element = findPos(currentIndex);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                currentIndex++;
                return element;
            }
        };
    }

    public int size() {
        return size;
    }
}

public class AdjacencyListGraph {
    List<GraphNode> nodes;

    public AdjacencyListGraph() {
        nodes = new List<GraphNode>();
    }

    /**
     * Agrega un nuevo nodo
     * @return Valor o nombre del nodo agregado
     */
    public String addNode() {
        String nodeValue = String.format("Node%s", nodes.size());
        nodes.add(new GraphNode<String>(nodeValue));

        return nodeValue;
    }

    /**
     * Agrega una conexión entre dos nodos
     * @param nodeValue1 Primer nodo
     * @param nodeValue2 Segundo nodo
     * @throws Exception
     */
    public void addConnection(String nodeValue1, String nodeValue2) throws Exception {
        List<GraphNode> temp = new List<GraphNode>();
        for (GraphNode node : nodes) {
            if (node.getValue().equals(nodeValue1) || node.getValue().equals(nodeValue2)) {
                temp.add(node);
            }
        }

        if (temp.size() > 0) {
            GraphNode graphNode1 = temp.findPos(0);
            GraphNode graphNode2 = temp.findPos(1);

            graphNode1.addConnection(graphNode2);
            graphNode2.addConnection(graphNode1);
        }
    }

    /**
     * Elimina un nodo
     * @param nodeValue Valor o nombre del nodo
     * @throws Exception
     */
    public void removeNode(String nodeValue) throws Exception {
        int position = 0;
        for (GraphNode node : nodes) {
            if (node.getValue().equals(nodeValue)) {
                List<GraphNode> connections = node.getConnections();
                for (GraphNode connection : connections) {
                    connection.removeConnection(node);
                }
                nodes.deleteAt(position);
                break;
            }
            position++;
        }
    }

    /**
     * Elimina una conexión entre dos nodos
     * @param nodeValue1 Primer nodo
     * @param nodeValue2 Segundo nodo
     * @throws Exception
     */
    public void removeConnection(String nodeValue1, String nodeValue2) throws Exception {
        List<GraphNode> temp = new List<GraphNode>();
        for (GraphNode node : nodes) {
            if (node.getValue().equals(nodeValue1) || node.getValue().equals(nodeValue2)) {
                temp.add(node);
            }
        }

        if (temp.size() > 0) {
            GraphNode graphNode1 = temp.findPos(0);
            GraphNode graphNode2 = temp.findPos(1);

            graphNode1.removeConnection(graphNode2);
            graphNode2.removeConnection(graphNode1);
        }
    }

    /**
     * Evalua si dos nodos estan interconectados
     * @param nodeValue1 Primer nodo
     * @param nodeValue2 Segundo nodo
     * @return true si existe una conexión
     * @throws Exception
     */
    public boolean areAdjacent(String nodeValue1, String nodeValue2) throws Exception {
        List<GraphNode> temp = new List<GraphNode>();
        boolean areAdjacent = false;
        for (GraphNode node : nodes) {
            if (node.getValue().equals(nodeValue1) || node.getValue().equals(nodeValue2)) {
                temp.add(node);
            }
        }

        if (temp.size() > 0) {
            GraphNode graphNode1 = temp.findPos(0);
            GraphNode graphNode2 = temp.findPos(1);

            areAdjacent = graphNode1.isAdjacent(graphNode2);
        }

        return areAdjacent;
    }

    /**
     * Imprime cada nodo del grafo con sus respectivas conexiones
     */
    public void printGraph() {
        for (GraphNode node : nodes) {
            System.out.print(node.getValue() + ": ");

            List<GraphNode> connections = node.getConnections();
            int index = 0;
            for (GraphNode connection : connections) {
                System.out.print(connection.getValue());
                if (index < connections.size()) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    /* Main */
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        AdjacencyListGraph graph = new AdjacencyListGraph();
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
                        String nodeValue = graph.addNode();
                        System.out.println(String.format("Se añadio: %s", nodeValue));
                        break;
                    case "b":
                        System.out.println("Ingresa los nodos que se conectaran");
                        System.out.println("\tIngresar el nombre del nodo con el siguiente patron:");
                        System.out.println("\t\t1) Primera letra mayuscula");
                        System.out.println("\t\t2) Cero espacios");
                        System.out.println("1er nodo:");
                        String node1 = scanner.nextLine();
                        System.out.println("2do nodo:");
                        String node2 = scanner.nextLine();

                        try {
                            graph.addConnection(node1, node2);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "c":
                        System.out.println("Ingresa el nodo que se eliminara");
                        System.out.println("\tIngresar el nombre del nodo con el siguiente patron:");
                        System.out.println("\t\t1) Primera letra mayuscula");
                        System.out.println("\t\t2) Cero espacios");
                        String node = scanner.nextLine();

                        try {
                            graph.removeNode(node);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "d":
                        System.out.println("Ingresa los nodos que componen la conexión que se eliminara");
                        System.out.println("\tIngresar el nombre del nodo con el siguiente patron:");
                        System.out.println("\t\t1) Primera letra mayuscula");
                        System.out.println("\t\t2) Cero espacios");
                        System.out.println("1er nodo:");
                        String _node1 = scanner.nextLine();
                        System.out.println("2do nodo:");
                        String _node2 = scanner.nextLine();

                        try {
                            graph.removeConnection(_node1, _node2);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "e":
                        System.out.println("Ingresa los nodos a evaluar");
                        System.out.println("1er nodo:");
                        String __node1 = scanner.nextLine();
                        System.out.println("2do nodo:");
                        String __node2 = scanner.nextLine();

                        try {
                            System.out.println(String.format("%s y %s %s son adyacentes", __node1, __node2, graph.areAdjacent(__node1, __node2) ? "si" : "no"));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "f":
                        graph.printGraph();
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
