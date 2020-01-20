package hello;

import hello.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;

public class FileDAO {
    private static final String INSERT_INTO_FILE = "insert into file(file_name, content) values (?,?)";
    private static final String SELECT_FILE_BY_NAME = "select id, file_name, content from file where file_name = ?";

    private JdbcTemplate jdbcTemplate;
    FileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public void create(File file) {
        if (jdbcTemplate == null) {
            System.out.println("template is null, autowired failed");
            return;

        }
        jdbcTemplate.update(INSERT_INTO_FILE,
         new Object[] { file.getName(), file.getContent()});
//            FieldUtils.setProtectedFieldValue("id", file, obtainPrimaryKey());

    }
    public File byName(String name) {
        return jdbcTemplate.queryForObject(SELECT_FILE_BY_NAME,
            new Object[] { name }, (rs, rowNumber) -> {
                    System.out.println("rs" + rs);
                    System.out.println("rowNumber" + rowNumber);
                String c = rs.getString("content");
                if (c==null) return null;
                File res = new File(name, c);
                res.setId(rs.getLong("ID"));
                return res;
            });
    }
}
