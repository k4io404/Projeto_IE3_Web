package com.controleDeAcesso.dao;

import com.controleDeAcesso.model.Casa;
import com.controleDeAcesso.view.CasaView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CasaDAO {
    // Gravar
    public int incluirCasa(Casa casa) throws SQLException {

        String sql = "INSERT INTO CASAS (casa_ender) VALUES (?)";

        // Abre e fecha o conector
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, casa.getEndereco());

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

    // Consultar
    public Casa consultarCasa(int casa_id) throws SQLException {

        String sql = "SELECT * FROM CASAS WHERE casa_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, casa_id);

            try (ResultSet rs = stmt.executeQuery()) {

                // cursor mostra a linha n-1
                if (rs.next()) {
                    Casa c = new Casa();
                    c.setId(rs.getInt("casa_id"));
                    c.setEndereco(rs.getString("casa_ender"));
                    return c;
                }
                return null;
            }
        }
    }

    // Deletar - Retorna boolean
    public boolean deletarCasa(int casa_id) throws SQLException {
        String sql = "DELETE FROM CASAS WHERE casa_id=? ";
        try (Connection conn = ConnectionFactory.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, casa_id);

            return stmt.executeUpdate() > 0;
        }
    }

    // Consultar casas no geral (arrayList)
    public List<Casa> consultarCasasGeral() throws SQLException {

        String sql = "SELECT * FROM CASAS";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {

                List<Casa> listaCasa = new ArrayList<>();

                // cursor mostra a linha n-1
                while (rs.next()) {
                    Casa c = new Casa();
                    c.setId(rs.getInt("casa_id"));
                    c.setEndereco(rs.getString("casa_ender"));
                    listaCasa.add(c);
                }
                return listaCasa;
            }
        }
    }

    // Consultar Views Casas
    public CasaView consultarCasaView(int casa_id) throws SQLException {

        String sql = "SELECT " +
                "a.casa_id, " +
                "a.casa_ender, " +
                "b.tipo_vinculo, " +
                "c.pessoa_nome " +
                "FROM CASAS a " +
                "INNER JOIN MORADOR_CASA b ON a.casa_id = b.casa_id " +
                "INNER JOIN PESSOAS c ON b.morador_id = c.pessoa_id " +
                "WHERE a.casa_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, casa_id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    CasaView casaView = new CasaView();
                    casaView.setId(rs.getInt("casa_id"));
                    casaView.setEndereco(rs.getString("casa_ender"));
                    casaView.setTipoVinculo(rs.getString("tipo_vinculo"));
                    casaView.setNomeMorador(rs.getString("pessoa_nome"));
                    return casaView;
                }

                return null; // não encontrou registro
            }
        }
    }


    // Consultar Views Casas
    public List<CasaView> consultarCasasViewGeral() throws SQLException {

        String sql = "SELECT " +
                    "a.casa_ender, " +
                    "b.tipo_vinculo, " +
                    "c.pessoa_nome " +
                    "FROM CASAS a " +
                    "INNER JOIN MORADOR_CASA b " +
                    "ON a.casa_id = b.casa_id " +
                    "INNER JOIN PESSOAS c " +
                    "ON b.morador_id = c.pessoa_id ";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {

                List<CasaView> listaCasaView = new ArrayList<>();

                // cursor mostra a linha n-1
                while (rs.next()) {
                    CasaView c = new CasaView();
                    c.setEndereco(rs.getString("casa_ender"));
                    c.setNomeMorador(rs.getString("pessoa_nome"));
                    c.setTipoVinculo(rs.getString("tipo_vinculo"));
                    listaCasaView.add(c);
                }
                return listaCasaView;
            }
        }
    }

    // Atualizar Casa
    // Atualizar - Retorna boolean
    public boolean atualizarCasa(Casa casa) throws SQLException {

        String sql = "UPDATE CASAS SET casa_ender=? WHERE casa_id=? ";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, casa.getEndereco());
            stmt.setInt(2, casa.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public List<Casa> consultarNumerosCasas() throws SQLException{

        String sql = "SELECT casa_id, casa_ender FROM CASAS";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {

                List<Casa> listaCasa = new ArrayList<>();

                // cursor mostra a linha n-1
                while (rs.next()) {
                    Casa c = new Casa();
                    c.setId(rs.getInt("casa_id"));
                    c.setEndereco(rs.getString("casa_ender"));
                    listaCasa.add(c);
                }
                return listaCasa;
            }
        }

    }

}
