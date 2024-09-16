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

        // Array para armazenar a população em cada sala
        double[] salas = new double[n + 1];
        double[] novoEstado = new double[n + 1];

        // Personagem inicia na sala central
        int salaInicial = (n + 1) / 2;
        salas[salaInicial] = 1.0;  // Começa com 1 personagem

        // Probabilidades conforme as regras do jogo
        double probDesaparecer = 1.0 / (2 * n);
        double probPermanecer = (n - 1.0) / (2 * n);
        double probMover = 0.5;

        boolean estabilizado = false;
        int ciclos = 0;  // Contador de ciclos para adicionar novos personagens
        int maxIteracoes = 10000;  // Limite de iterações para evitar loop infinito

        // Simulação até a estabilização ou até atingir o número máximo de iterações
        while (!estabilizado && ciclos < maxIteracoes) {
            estabilizado = true;
            novoEstado = new double[n + 1];  // Novo estado para a próxima iteração

            // Atualiza as salas intermediárias (de 2 até n-1)
            for (int i = 2; i <= n - 1; i++) {
                novoEstado[i] = (salas[i - 1] * probMover) + (salas[i + 1] * probMover);
            }

            // Atualiza as salas das pontas (salas 1 e n)
            novoEstado[1] = (salas[2] * probMover) + (salas[1] * probPermanecer);
            novoEstado[n] = (salas[n - 1] * probMover) + (salas[n] * probPermanecer);

            // Considera a probabilidade de desaparecimento nas salas 1 e n
            novoEstado[1] *= (1 - probDesaparecer);
            novoEstado[n] *= (1 - probDesaparecer);

            // Verifica se o sistema estabilizou (diferença menor que um limite)
            for (int i = 1; i <= n; i++) {
                if (Math.abs(novoEstado[i] - salas[i]) > 1e-9) {  // Usar uma diferença maior para garantir que estabilize
                    estabilizado = false;
                }
            }

            // Adiciona mais um personagem no início do ciclo
            if (!estabilizado) {
                novoEstado[salaInicial] += 1;  // Novo personagem sempre entra na sala central
            }

            // Atualiza o estado das salas para o próximo ciclo
            salas = novoEstado;
            ciclos++;
        }

        // Calcula a população total após a estabilização
        double totalPersonagens = 0;
        for (int i = 1; i <= n; i++) {
            totalPersonagens += salas[i];
        }

        // Imprime o resultado final
        System.out.printf("População para %d salas: %.0f atores\n", n, totalPersonagens);
    }
}
