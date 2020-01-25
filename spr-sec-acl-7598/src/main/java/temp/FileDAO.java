package hello;

import hello.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

public class FileDAO {
    private static final String INSERT_INTO_FILE = "insert into file(file_name, content) values (?,?)";
    private static final String SELECT_FILE_BY_NAME = "select id, file_name, content from file where file_name = ?";
    private static final String SELECT_FROM_USERS = "SELECT sid FROM acl_sid";

    private JdbcTemplate jdbcTemplate;


//    @Autowired
    private MutableAclService aclService;

    FileDAO(JdbcTemplate jdbcTemplate, MutableAclService aclService) {

        this.jdbcTemplate = jdbcTemplate;
        Assert.notNull(aclService, "MutableAclService required");
        this.aclService = aclService;
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

    public void createUser(String sidd) {
        Sid sid = new PrincipalSid("Samantha");
        File f = new File("mew", "23");
        f.setId(31321L);
        ObjectIdentity identity = new ObjectIdentityImpl(f);
        Permission p = BasePermission.READ;
//        MutableAcl acl = aclService.createAcl(identity);



//        TODO: add acl service, create isntance of it


    }

    public String[] getUsers() {
        return jdbcTemplate.query(SELECT_FROM_USERS,
                (rs, rowNumber) -> rs.getString("sid")).toArray(new String[] {});

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
