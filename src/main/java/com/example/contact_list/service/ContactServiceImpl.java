package com.example.contact_list.service;

import com.example.contact_list.mapper.ContactRowMapper;
import com.example.contact_list.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM contacts_schema.contact";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Contact save(Contact contact) {
        int id = 1;

        String sqlId = "SELECT * FROM contacts_schema.contact ORDER BY id DESC LIMIT 1";
        Contact existedContactId = DataAccessUtils.singleResult(
                jdbcTemplate.query(sqlId, new ContactRowMapper())
        );
        if (existedContactId != null) {
            contact.setId(existedContactId.getId() + 1);
        } else {
            contact.setId(id);
        }

        String sql = "INSERT INTO contacts_schema.contact (firstname, lastname, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(),
                contact.getPhone(), contact.getId());
        return contact;
    }

    @Override
    public Contact update(Contact contact) {

        String sqlUpdate = "UPDATE contacts_schema.contact SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdate, contact.getFirstName(), contact.getLastName(), contact.getEmail(),
                contact.getPhone(), contact.getId());
        return contact;

    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM contacts_schema.contact WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Contact findById(Integer id) {
        String sql = "SELECT * FROM contacts_schema.contact WHERE id = ?";

        Contact existedContact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );

        return existedContact;
    }
}
