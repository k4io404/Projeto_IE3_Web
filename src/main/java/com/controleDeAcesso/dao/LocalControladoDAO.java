package com.controleDeAcesso.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.controleDeAcesso.model.LocalControlado;

public class LocalControladoDAO {

    // Gravar
    public int incluirLocalControlado(LocalControlado localControlado) throws SQLException {

        String sql = "INSERT INTO LOCAIS_CONTROLADOS (local_nome, local_descricao) VALUES (?,?)";

        // Abre e fecha o conector
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, localControlado.getNome());
            stmt.setString(2, localControlado.getDescricao());

            // Linha afetada pela ação
            int affectedRows = stmt.executeUpdate();

            // Cursos sempre fica um antes
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // id gerado
                }
            }

            // Retornar a linha afetada pela ação
            return affectedRows;
        }
    }

    // Atualizar - Retorna boolean
    public boolean atualizarLocalControlado(LocalControlado localControlado) throws SQLException {

        String sql = "UPDATE LOCAIS_CONTROLADOS SET local_nome=? local_descricao=?  WHERE local_id=? ";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, localControlado.getNome());
            stmt.setString(2, localControlado.getDescricao());
            stmt.setInt(3, localControlado.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Consultar
    public LocalControlado consultarLocalControlado(int id) throws SQLException {

        String sql = "SELECT * FROM LOCAIS_CONTROLADOS WHERE local_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                // cursor mostra a linha n-1
                if (rs.next()) {
                    LocalControlado l = new LocalControlado();
                    l.setId(rs.getInt("local_id"));
                    l.setNome(rs.getString("local_id"));
                    l.setDescricao(rs.getString("local_descricao"));
                    return l;
                }
                return null;
            }
        }
    }

    // Consultar de forma geral
    public List<LocalControlado> consultarLocaisControladosGeral() throws SQLException {

        String sql = "SELECT * FROM LOCAIS_CONTROLADOS";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                List<LocalControlado> listaLocaisControlados = new ArrayList<>();

                // cursor mostra a linha n-1
                while (rs.next()) {
                    LocalControlado l = new LocalControlado();
                    l.setId(rs.getInt("local_id"));
                    l.setNome(rs.getString("local_nome"));
                    l.setDescricao(rs.getString("local_descricao"));
                    listaLocaisControlados.add(l);
                }
                return listaLocaisControlados;
            }
        }
    }


    public List<LocalControlado> consultarNomesLocais() throws SQLException {

        String sql = "SELECT local_id, local_nome FROM LOCAIS_CONTROLADOS";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                List<LocalControlado> listaNomesLocais = new ArrayList<>();

                // cursor mostra a linha n-1
                while (rs.next()) {
                    LocalControlado l = new LocalControlado();
                    l.setId(rs.getInt("local_id"));
                    l.setNome(rs.getString("local_nome"));
                    listaNomesLocais.add(l);
                }
                return listaNomesLocais;
            }
        }
    }




    // Deletar - Retorna boolean
    public boolean deletarLocalControlado(int local_id) throws SQLException {

        String sql = "DELETE FROM LOCAIS_CONTROLADOS WHERE local_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, local_id);

            return stmt.executeUpdate() > 0;
        }
    }

}
