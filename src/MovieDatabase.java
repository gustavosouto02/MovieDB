import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MovieDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/moviedb";
    private static final String USERNAME = "root"; // Substitua pelo seu usuário
    private static final String PASSWORD = "Gustavo02"; // Substitua pela sua senha
    private static int selectedMovieId = -1; // Armazena o ID do filme selecionado

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Movie Database");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> movieList = new JList<>(listModel);
            movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            frame.add(new JScrollPane(movieList), BorderLayout.WEST);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(6, 2));
            panel.setBorder(BorderFactory.createTitledBorder("Movie Details"));

            JLabel titleLabel = new JLabel("Title:");
            JTextField titleField = new JTextField();
            JLabel yearLabel = new JLabel("Year:");
            JTextField yearField = new JTextField();
            JLabel directorLabel = new JLabel("Director:");
            JTextField directorField = new JTextField();

            JButton insertButton = new JButton("Insert Movie");
            JButton loadButton = new JButton("Load Movies");
            JButton editButton = new JButton("Edit Movie");
            JButton deleteButton = new JButton("Delete Movie");

            panel.add(titleLabel);
            panel.add(titleField);
            panel.add(yearLabel);
            panel.add(yearField);
            panel.add(directorLabel);
            panel.add(directorField);
            panel.add(insertButton);
            panel.add(loadButton);
            panel.add(editButton);
            panel.add(deleteButton);

            frame.add(panel, BorderLayout.SOUTH);

            loadButton.addActionListener(e -> loadMovies(textArea, titleField, yearField, directorField, listModel));

            insertButton.addActionListener(e -> {
                String title = titleField.getText();
                String year = yearField.getText();
                String director = directorField.getText();
                insertMovie(title, year, director);
                clearFields(titleField, yearField, directorField);
                loadMovies(textArea, titleField, yearField, directorField, listModel);
            });

            editButton.addActionListener(e -> {
                if (selectedMovieId != -1) {
                    String title = titleField.getText();
                    String year = yearField.getText();
                    String director = directorField.getText();
                    editMovie(selectedMovieId, title, year, director);
                    clearFields(titleField, yearField, directorField);
                    loadMovies(textArea, titleField, yearField, directorField, listModel);
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione um filme para editar.");
                }
            });

            deleteButton.addActionListener(e -> {
                if (selectedMovieId != -1) {
                    deleteMovie(selectedMovieId);
                    clearFields(titleField, yearField, directorField);
                    loadMovies(textArea, titleField, yearField, directorField, listModel);
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione um filme para excluir.");
                }
            });

            movieList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = movieList.getSelectedValue();
                    if (selectedValue != null) {
                        String[] parts = selectedValue.split(" ");
                        selectedMovieId = Integer.parseInt(parts[0]);
                        titleField.setText(parts[1]);
                        yearField.setText(parts[2]);
                        directorField.setText(parts[3]);
                    }
                }
            });

            frame.setVisible(true);
        });
    }

    private static void loadMovies(JTextArea textArea, JTextField titleField, JTextField yearField, JTextField directorField, DefaultListModel<String> listModel) {
        String query = "SELECT * FROM movies";
        StringBuilder sb = new StringBuilder();
        listModel.clear();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int year = resultSet.getInt("year");
                String director = resultSet.getString("director");
                sb.append(id).append(" ").append(title).append(" ").append(year).append(" ").append(director).append("\n");
                listModel.addElement(id + " " + title + " " + year + " " + director);
            }

            textArea.setText(sb.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertMovie(String title, String year, String director) {
        String query = "INSERT INTO movies (title, year, director) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, Integer.parseInt(year));
            preparedStatement.setString(3, director);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Filme inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inserir filme: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um ano válido.");
        }
    }

    private static void editMovie(int id, String title, String year, String director) {
        String query = "UPDATE movies SET title = ?, year = ?, director = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, Integer.parseInt(year));
            preparedStatement.setString(3, director);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Filme atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar filme: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um ano válido.");
        }
    }

    private static void deleteMovie(int id) {
        String query = "DELETE FROM movies WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Filme excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir filme: " + e.getMessage());
        }
    }

    private static void clearFields(JTextField titleField, JTextField yearField, JTextField directorField) {
        titleField.setText("");
        yearField.setText("");
        directorField.setText("");
    }
}
