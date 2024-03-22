package src;

import java.util.List;
import java.util.Scanner;

import src.classes.Simulation;
import src.classes.Utilities;
import src.searchs.*;

public class Menu {
  private Scanner scanner;

  public Menu() {
    scanner = new Scanner(System.in);
  }

  public void displayMenu() {
    System.out.println("Seleccione el algoritmo a ejecutar:");
    System.out.println("1. Amplitud");
    System.out.println("2. Costo");
    System.out.println("3. Depth");
    System.out.println("4. Salir");
  }

  public void selectOption() {
    int option = scanner.nextInt();
    scanner.nextLine();
    switch (option) {
      case 1:
        Amplitude amplitude = new Amplitude("./src/professor_test.txt");
        executeAlgorithm(amplitude);
        break;
      case 2:
        Cost cost = new Cost("./src/professor_test.txt");
        executeAlgorithm(cost);
        break;
      case 3:
        Depth depth = new Depth("./src/professor_test.txt");
        executeAlgorithm(depth);
        break;
      case 4:
        System.out.println("Saliendo...");
        System.exit(0);
      default:
        System.out.println("Opción no válida. Intente de nuevo.");
    }
    System.out.println("Presione Enter para continuar...");
    scanner.nextLine();
  }

  private void executeAlgorithm(Search search) {
    List<Simulation.Operator> solution = search.run();
    List<Simulation.Operator> inverted_solution = Utilities.invert_solution(solution);

    System.out.print("START");
    for (Simulation.Operator operator : inverted_solution)
      System.out.print(Utilities.operator_to_string(operator) + "->");
    System.out.print("GOAL\n");

    System.out.println("Expanded nodes: " + search.get_expanded_nodes());
    System.out.println("Cost: " + search.get_cost());
    System.out.println("Depth: " + search.get_depth());

    System.out.println();
  }
}