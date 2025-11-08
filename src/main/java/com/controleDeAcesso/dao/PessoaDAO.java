package com.controleDeAcesso.dao;


import com.controleDeAcesso.model.Pessoa;
import com.controleDeAcesso.util.TipoPessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    // Gravar
    //public int incluirPessoa(Pessoa pessoa) throws SQLException {
        //String sql = "INSERT INTO PESSOAS (pessoa_nome, pessoa_cpf, pessoa_email, pessoa_telef, pessoa_data_nasc) VALUES (?,?,?,?,?)";

        // Abre e fecha o conector
        //try (Connection conn = ConnectionFactory.getConnection();
             //PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            //stmt.setString(1, pessoa.getNome());
            //stmt.setString(2, pessoa.getCpf());
            //stmt.setString(3, pessoa.getEmail());
            //stmt.setString(4, pessoa.getTelefone());
            //stmt.setDate(5, new java.sql.Date(pessoa.getDataNasc().getTime()));

            // Linha afetada pela ação
            //int affectedRows = stmt.executeUpdate();

            // Cursos sempre fica um antes
            //try (ResultSet rs = stmt.getGeneratedKeys()) {
                //if (rs.next()) {
                    //return rs.getInt(1); // id gerado
                    // }
                //}

            // Retornar a linha afetada pela ação
            //return affectedRows;
            //    }
    //}

    protected int incluirPessoa(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO PESSOAS (pessoa_nome, pessoa_cpf, pessoa_email, pessoa_telef, pessoa_data_nasc, pessoa_ativa, pessoa_tipo) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getEmail());
            stmt.setString(4, pessoa.getTelefone());
            stmt.setDate(5, new java.sql.Date(pessoa.getDataNasc().getTime()));
            // garante que não será inserido NULL (evita erro quando coluna não tem DEFAULT)
            stmt.setBoolean(6, true);
            stmt.setString(7, pessoa.getTipo());

            int affectedRows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            // Cursos sempre fica um antes
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // id gerado
                }
            }

            return affectedRows;
        }
    }

    // Consultar Geral - Retorna um arrayList
    public List<Pessoa> consultarPessoasGeral( ) throws SQLException {

        String sql = "SELECT *  FROM PESSOAS";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                List<Pessoa> listaPessoas = new ArrayList<>();

                while (rs.next()) {
                    Pessoa p = new Pessoa();
                    p.setId(rs.getInt("pessoa_id"));
                    p.setNome(rs.getString("pessoa_nome"));
                    p.setCpf(rs.getString("pessoa_cpf"));
                    p.setEmail(rs.getString("pessoa_email"));
                    p.setTelefone(rs.getString("pessoa_telef"));
                    p.setTipo(rs.getString("pessoa_tipo"));
                    p.setAtiva(rs.getBoolean("pessoa_ativa"));
                    Date d = rs.getDate("pessoa_data_nasc");
                    p.setDataNasc(d);
                    listaPessoas.add(p);
                }
                return listaPessoas;
            }
        }
    }


    public List<Pessoa> consultarNomesMoradores() throws SQLException {
        //----------------------------
        //Depois colocar pessoas associadas maiores de 18 anos comparando a data de nascimento
        //----------------------------
        String sql = "SELECT pessoa_id, pessoa_nome FROM PESSOAS WHERE pessoa_tipo = 'M'";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                List<Pessoa> listaPessoas = new ArrayList<>();

                // cursor mostra a linha n-1
                while (rs.next()) {
                    Pessoa p = new Pessoa();
                    p.setId(rs.getInt("pessoa_id"));
                    p.setNome(rs.getString("pessoa_nome"));
                    listaPessoas.add(p);
                }
                return listaPessoas;
            }
        }
    }



    // Consultar por CPF - Retorna um objeto do tipo pessoa
    public Pessoa consultarPessoaCpf(String cpf) throws SQLException {

        String sql = "SELECT *  FROM PESSOAS WHERE pessoa_cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {

                // cursor mostra a linha n-1
                if (rs.next()) {
                    Pessoa p = new Pessoa();
                    p.setId(rs.getInt("pessoa_id"));
                    p.setNome(rs.getString("pessoa_nome"));
                    p.setCpf(rs.getString("pessoa_cpf"));
                    p.setEmail(rs.getString("pessoa_email"));
                    p.setTelefone(rs.getString("pessoa_telef"));
                    p.setTipo(rs.getString("pessoa_tipo"));
                    Date d = rs.getDate("pessoa_data_nasc");
                    p.setDataNasc(d);
                    return p;
                }
                return null;
            }
        }
    }

    // Consultar por Nome - Retorna um objeto do tipo pessoa
    public Pessoa consultarPessoaNome(String pessoa_nome) throws SQLException {

        String sql = "SELECT *  FROM PESSOAS WHERE pessoa_nome = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa_nome);

            try (ResultSet rs = stmt.executeQuery()) {

                // cursor mostra a linha n-1
                if (rs.next()) {
                    Pessoa p = new Pessoa();
                    p.setId(rs.getInt("pessoa_id"));
                    p.setNome(rs.getString("pessoa_nome"));
                    p.setCpf(rs.getString("pessoa_cpf"));
                    p.setEmail(rs.getString("pessoa_email"));
                    p.setTelefone(rs.getString("pessoa_telef"));
                    p.setTipo(rs.getString("pessoa_tipo"));
                    Date d = rs.getDate("pessoa_data_nasc");
                    p.setDataNasc(d);
                    return p;
                }
                return null;
            }
        }
    }

    private String comandoConsultarPessoasPorTipo(TipoPessoa tipoPessoa){
        if(tipoPessoa.equals(TipoPessoa.MORADOR)){
            return "SELECT A.* FROM PESSOAS A INNER JOIN MORADORES B ON A.pessoa_id = B.morador_id";
        }
        else if (tipoPessoa.equals(TipoPessoa.PRESTADOR)){
            return  "SELECT A.* FROM PESSOAS A INNER JOIN PRESTADORES B ON A.pessoa_id = B.prestador_id";
        }
        else if(tipoPessoa.equals(TipoPessoa.VISITANTE)){
            return  "SELECT A.* FROM PESSOAS A INNER JOIN VISITANTES B ON A.pessoa_id = B.visitante_id";
        }
        else {
            throw new IllegalArgumentException("Tipo de pessoa inválido: " + tipoPessoa);
        }
    }

    public List<Pessoa> consultarPessoasPorTipo(TipoPessoa tipoPessoa) throws SQLException{

        String sql = comandoConsultarPessoasPorTipo(tipoPessoa);

        try (Connection conn = ConnectionFactory.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                List<Pessoa> listaPessoas = new ArrayList<>();

                while (rs.next()) {
                    Pessoa p = new Pessoa();
                    p.setId(rs.getInt("pessoa_id"));
                    p.setNome(rs.getString("pessoa_nome"));
                    p.setCpf(rs.getString("pessoa_cpf"));
                    p.setEmail(rs.getString("pessoa_email"));
                    p.setTelefone(rs.getString("pessoa_telef"));
                    p.setTipo(rs.getString("pessoa_tipo"));
                    Date d = rs.getDate("pessoa_data_nasc");
                    p.setDataNasc(d);
                    listaPessoas.add(p);
                }
                return listaPessoas;
            }
        }
    }

    // Atualizar - Retorna boolean
    protected boolean atualizarPessoa(Pessoa pessoa) throws SQLException {

        String sql = "UPDATE PESSOAS SET pessoa_nome=?, pessoa_cpf=?, pessoa_email=?, pessoa_telef=?, pessoa_data_nasc=?, pessoa_tipo=?  WHERE pessoa_id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getEmail());
            stmt.setString(4, pessoa.getTelefone());
            stmt.setDate(5, new java.sql.Date(pessoa.getDataNasc().getTime()));
            stmt.setInt(6, pessoa.getId());
            stmt.setString(7, pessoa.getTipo());

            return stmt.executeUpdate() > 0;
        }
    }

    // Deletar - Retorna boolean
    protected boolean deletarPessoa(int id) throws SQLException {

        String sql = "UPDATE PESSOAS SET pessoa_ativa = ? WHERE pessoa_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, false);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }

//        String sql = "DELETE FROM PESSOAS WHERE pessoa_id = ?";
//        try (Connection conn = ConnectionFactory.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1,pessoa.getId());
//            return stmt.executeUpdate() > 0;
//        }
    }

    public boolean reincluirPessoa(int pessoa_id) throws SQLException{
        String sql = "UPDATE PESSOAS SET pessoa_ativa = ? WHERE pessoa_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, true);
            stmt.setInt(2, pessoa_id);
            return stmt.executeUpdate() > 0;
        }
    }
}