import java.util.Scanner;

public class Simuleitor {
    public static void main(String[] args) {
        // Recebe o número de salas n como argumento
        if (args.length != 1) {
            System.out.println("Uso: java Simuleitor <número de salas>");
            return;
        }

        int n = Integer.parseInt(args[0]);

        // Verifica se n é válido
        if (n <= 0) {
            System.out.println("Número de salas deve ser positivo.");
            return;
        }

        double[] salas = new double[n + 1]; 
        int salaInicial = (n + 1) / 2;

        salas[salaInicial] = 1.0;

        double probDesaparecer = 1.0 / (2 * n);
        double probPermanecer = (n - 1.0) / (2 * n);
        double probMover = 0.5;

        boolean estabilizado = false;
        while (!estabilizado) {
            estabilizado = true;  
            double[] novoEstado = new double[n + 1];

            for (int i = 2; i <= n - 1; i++) {
                novoEstado[i] = (salas[i - 1] * probMover) + (salas[i + 1] * probMover);
            }

            novoEstado[1] = (salas[2] * probMover) + (salas[1] * probPermanecer);
            novoEstado[n] = (salas[n - 1] * probMover) + (salas[n] * probPermanecer);

            novoEstado[1] += salas[1] * probDesaparecer;
            novoEstado[n] += salas[n] * probDesaparecer;

            for (int i = 1; i <= n; i++) {
                if (Math.abs(novoEstado[i] - salas[i]) > 1e-9) {
                    estabilizado = false;
                    break;
                }
            }

            salas = novoEstado;
        }

        double totalPersonagens = 0;
        for (int i = 1; i <= n; i++) {
            totalPersonagens += salas[i];
        }

        System.out.printf("População para %d salas: %.0f atores\n", n, totalPersonagens);
    }
}
