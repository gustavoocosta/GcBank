import java.util.ArrayList;
import java.util.List;

// Classe principal do programa
public class gcbank {
    public static void main(String[] args) {
        // Criando uma empresa
        Empresa empresa = new Empresa("Empresa A", "12345678901234", 0.0);
        
        // Criando um cliente
        Cliente cliente = new Cliente("Cliente B", "123.456.789-01", 1000.0);
        
        // Realizando uma transação (depósito)
        double valorDeposito = 500.0;
        empresa.realizarTransacao(cliente, valorDeposito, "depósito");
    }
}

// Classe para representar um usuário genérico
class Usuario {
    private String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

// Classe para representar um cliente
class Cliente extends Usuario {
    private String cpf;
    private double saldo;

    public Cliente(String nome, String cpf, double saldo) {
        super(nome);
        this.cpf = cpf;
        this.saldo = saldo;
    }

    public String getCpf() {
        return cpf;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}

// Classe para representar uma empresa
class Empresa extends Usuario {
    private String cnpj;
    private double saldo;
    private List<Taxa> taxas;

    public Empresa(String nome, String cnpj, double saldo) {
        super(nome);
        this.cnpj = cnpj;
        this.saldo = saldo;
        this.taxas = new ArrayList<>();
        // Adicionando uma taxa de exemplo
        this.taxas.add(new Taxa("Taxa de Administração", 0.05)); // 5%
    }

    public String getCnpj() {
        return cnpj;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Método para realizar uma transação (depósito ou saque)
    public void realizarTransacao(Cliente cliente, double valor, String tipo) {
        if (tipo.equals("depósito")) {
            double valorComTaxa = valor - (valor * calcularTaxaTotal());
            cliente.setSaldo(cliente.getSaldo() - valor);
            this.saldo += valorComTaxa;
            System.out.println("Depósito de R$" + valor + " realizado com sucesso para " + getNome());
            // Enviar callback para a empresa (simulação)
            enviarCallback();
            // Enviar notificação para o cliente (simulação)
            enviarNotificacao(cliente, "Depósito de R$" + valor + " realizado com sucesso em sua conta.");
        } else if (tipo.equals("saque")) {
            // Implementar lógica para saque
        } else {
            System.out.println("Tipo de transação inválido.");
        }
    }

    // Método para calcular o total das taxas de administração
    private double calcularTaxaTotal() {
        double taxaTotal = 0.0;
        for (Taxa taxa : taxas) {
            taxaTotal += taxa.getValor();
        }
        return taxaTotal;
    }

    // Método para enviar callback para a empresa
    private void enviarCallback() {
        // Simulação de envio de callback para a empresa
        System.out.println("Callback enviado para " + getNome());
    }

    // Método para enviar notificação para o cliente
    private void enviarNotificacao(Cliente cliente, String mensagem) {
        // Simulação de envio de notificação para o cliente (pode ser e-mail, SMS, etc.)
        System.out.println("Notificação enviada para " + cliente.getNome() + ": " + mensagem);
    }
}

// Classe para representar uma taxa
class Taxa {
    private String nome;
    private double valor;

    public Taxa(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }
}
