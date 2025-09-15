package gcbank.java.gcbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootApplication
public class GcbankApplication {
    public static void main(String[] args) {
        SpringApplication.run(GcbankApplication.class, args);
        
        // Exemplo de uso das classes do sistema bancário
        executarExemplo();
    }

    private static void executarExemplo() {
        System.out.println("=== GCBANK - Sistema Bancário Virtual ===");
        System.out.println();
        
        // Criando uma empresa
        Empresa empresa = new Empresa("Banco GC", "12345678901234", 10000.0);
        
        // Criando um cliente
        Cliente cliente = new Cliente("João Silva", "123.456.789-01", 1000.0);
        
        System.out.println("Cliente: " + cliente.getNome() + " - Saldo inicial: R$" + cliente.getSaldo());
        System.out.println("Empresa: " + empresa.getNome() + " - Saldo inicial: R$" + empresa.getSaldo());
        System.out.println();
        
        // Realizando transações
        empresa.realizarTransacao(cliente, 500.0, "deposito");
        System.out.println("Saldo do cliente após depósito: R$" + cliente.getSaldo());
        System.out.println("Saldo da empresa após depósito: R$" + empresa.getSaldo());
        System.out.println();
        
        empresa.realizarTransacao(cliente, 200.0, "saque");
        System.out.println("Saldo do cliente após saque: R$" + cliente.getSaldo());
        System.out.println("Saldo da empresa após saque: R$" + empresa.getSaldo());
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
    
    public void setNome(String nome) {
        this.nome = nome;
    }
}

// Classe para representar um cliente
class Cliente extends Usuario {
    private String cpf;
    private BigDecimal saldo;

    public Cliente(String nome, String cpf, double saldo) {
        super(nome);
        this.cpf = cpf;
        this.saldo = BigDecimal.valueOf(saldo).setScale(2, RoundingMode.HALF_UP);
    }

    public String getCpf() {
        return cpf;
    }

    public double getSaldo() {
        return saldo.doubleValue();
    }

    public void setSaldo(double saldo) {
        this.saldo = BigDecimal.valueOf(saldo).setScale(2, RoundingMode.HALF_UP);
    }

    public void adicionarAoSaldo(double valor) {
        this.saldo = this.saldo.add(BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP));
    }

    public boolean subtrairDoSaldo(double valor) {
        BigDecimal novoSaldo = this.saldo.subtract(BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP));
        if (novoSaldo.compareTo(BigDecimal.ZERO) >= 0) {
            this.saldo = novoSaldo;
            return true;
        }
        return false;
    }
}

// Classe para representar uma empresa
class Empresa extends Usuario {
    private String cnpj;
    private BigDecimal saldo;
    private List<Taxa> taxas;

    public Empresa(String nome, String cnpj, double saldo) {
        super(nome);
        this.cnpj = cnpj;
        this.saldo = BigDecimal.valueOf(saldo).setScale(2, RoundingMode.HALF_UP);
        this.taxas = new ArrayList<>();
        // Adicionando taxas de exemplo
        this.taxas.add(new Taxa("Taxa de Administração", 0.02)); // 2%
        this.taxas.add(new Taxa("Taxa de Operação", 0.01)); // 1%
    }

    public String getCnpj() {
        return cnpj;
    }

    public double getSaldo() {
        return saldo.doubleValue();
    }

    public void setSaldo(double saldo) {
        this.saldo = BigDecimal.valueOf(saldo).setScale(2, RoundingMode.HALF_UP);
    }
    
    public void adicionarAoSaldo(double valor) {
        this.saldo = this.saldo.add(BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP));
    }

    // Método para realizar uma transação (depósito ou saque)
    public boolean realizarTransacao(Cliente cliente, double valor, String tipo) {
        if (valor <= 0) {
            System.out.println("Erro: Valor deve ser positivo.");
            return false;
        }
        
        switch (tipo.toLowerCase()) {
            case "deposito":
                return realizarDeposito(cliente, valor);
            case "saque":
                return realizarSaque(cliente, valor);
            default:
                System.out.println("Erro: Tipo de transação inválido. Use 'deposito' ou 'saque'.");
                return false;
        }
    }
    
    private boolean realizarDeposito(Cliente cliente, double valor) {
        double taxaTotal = calcularTaxaTotal();
        double valorTaxa = valor * taxaTotal;
        double valorLiquido = valor - valorTaxa;
        
        cliente.adicionarAoSaldo(valorLiquido);
        this.adicionarAoSaldo(valorTaxa);
        
        System.out.println("Depósito realizado com sucesso!");
        System.out.println("Valor bruto: R$" + String.format("%.2f", valor));
        System.out.println("Taxa aplicada: R$" + String.format("%.2f", valorTaxa) + " (" + String.format("%.2f", taxaTotal * 100) + "%)");
        System.out.println("Valor líquido creditado: R$" + String.format("%.2f", valorLiquido));
        
        enviarCallback("DEPOSITO", valor, cliente);
        enviarNotificacao(cliente, "Depósito de R$" + String.format("%.2f", valorLiquido) + " creditado em sua conta.");
        
        return true;
    }
    
    private boolean realizarSaque(Cliente cliente, double valor) {
        double taxaTotal = calcularTaxaTotal();
        double valorTaxa = valor * taxaTotal;
        double valorTotal = valor + valorTaxa;
        
        if (cliente.subtrairDoSaldo(valorTotal)) {
            this.adicionarAoSaldo(valorTaxa);
            
            System.out.println("Saque realizado com sucesso!");
            System.out.println("Valor solicitado: R$" + String.format("%.2f", valor));
            System.out.println("Taxa aplicada: R$" + String.format("%.2f", valorTaxa) + " (" + String.format("%.2f", taxaTotal * 100) + "%)");
            System.out.println("Valor total debitado: R$" + String.format("%.2f", valorTotal));
            
            enviarCallback("SAQUE", valor, cliente);
            enviarNotificacao(cliente, "Saque de R$" + String.format("%.2f", valor) + " realizado em sua conta.");
            
            return true;
        } else {
            System.out.println("Erro: Saldo insuficiente para realizar o saque.");
            System.out.println("Saldo atual: R$" + String.format("%.2f", cliente.getSaldo()));
            System.out.println("Valor necessário: R$" + String.format("%.2f", valorTotal));
            return false;
        }
    }

    // Método para calcular o total das taxas de administração
    private double calcularTaxaTotal() {
        return taxas.stream()
                   .mapToDouble(Taxa::getValor)
                   .sum();
    }

    // Método para enviar callback para a empresa
    private void enviarCallback(String tipoTransacao, double valor, Cliente cliente) {
        System.out.println("[CALLBACK] Transação " + tipoTransacao + " de R$" + String.format("%.2f", valor) + 
                          " para cliente " + cliente.getNome() + " processada por " + getNome());
    }

    // Método para enviar notificação para o cliente
    private void enviarNotificacao(Cliente cliente, String mensagem) {
        System.out.println("[NOTIFICAÇÃO] Para " + cliente.getNome() + ": " + mensagem);
    }
    
    // Método para consultar saldo do cliente
    public void consultarSaldo(Cliente cliente) {
        System.out.println("Saldo atual de " + cliente.getNome() + ": R$" + String.format("%.2f", cliente.getSaldo()));
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
    
    public void setValor(double valor) {
        this.valor = valor;
    }
}